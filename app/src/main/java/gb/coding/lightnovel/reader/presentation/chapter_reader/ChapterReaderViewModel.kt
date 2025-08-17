@file:OptIn(ExperimentalCoroutinesApi::class)

package gb.coding.lightnovel.reader.presentation.chapter_reader

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gb.coding.lightnovel.core.domain.model.KnowledgeLevel
import gb.coding.lightnovel.core.domain.util.onError
import gb.coding.lightnovel.core.domain.util.onSuccess
import gb.coding.lightnovel.reader.domain.repository.ImageRepository
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import gb.coding.lightnovel.reader.domain.repository.SavedWordRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChapterReaderViewModel(
    savedStateHandle: SavedStateHandle,
    private val novelRepository: NovelRepository,
    private val savedWordRepository: SavedWordRepository,
    private val imageRepository: ImageRepository,
) : ViewModel() {

    private val _events = Channel<ChapterReaderEvent>()
    val events = _events.receiveAsFlow()

    private var wordCollectJob: Job? = null

    private val _state = MutableStateFlow(ChapterReaderState())
    val state = _state.asStateFlow()

    init {
        val chapterId: String = checkNotNull(savedStateHandle["chapterId"]) {
            println("ChapterReaderViewModel | init | Error getting chapterId from savedStateHandle")
        }

        viewModelScope.launch {
            println("ChapterReaderViewModel | init | Loading saved words")
            savedWordRepository
                .getAllWords()
                .collectLatest { words ->
                    _state.value = _state.value.copy(savedWords = words)
                }
        }

        viewModelScope.launch {
            novelRepository
                .getChapterById(chapterId)
                .onSuccess { chapter ->
                    println("ChapterReaderViewModel | Success getting chapter \"$chapterId\"")

                    _state.value = _state.value.copy(chapter = chapter)

                    novelRepository
                        .getChaptersByNovelId(chapter.novelId)
                        .onSuccess { allNovelChapters ->
                            _state.value = _state.value.copy(
                                chapterList = allNovelChapters,
                                currentChapterIndex = allNovelChapters.indexOfFirst { it.id == chapterId },
                                isLoading = false,
                            )
                        }
                        .onError { error ->
                            println("ChapterReaderViewModel | Error getting chapters for novel \"${chapter.novelId}\" Error: $error")
                            _state.value = _state.value.copy(isLoading = false)
                        }
                }
                .onError { error ->
                    println("ChapterReaderViewModel | Error getting chapter \"$chapterId\"\nError: $error")
                    _state.value = _state.value.copy(isLoading = false)
                }
        }
    }

    fun onAction(action: ChapterReaderAction) {
        when (action) {
            ChapterReaderAction.OnScreenClicked -> {
                println("ChapterReaderViewModel | OnScreenTap")
                _state.value = _state.value.copy(isOverlayVisible = !_state.value.isOverlayVisible)
            }

            ChapterReaderAction.OnNextChapterClicked -> {
                println("ChapterReaderViewModel | OnNextChapterClicked")
                val index = _state.value.currentChapterIndex
                val chapters = _state.value.chapterList
                if (index < chapters.lastIndex) {
                    _state.value = _state.value.copy(currentChapterIndex = index + 1)
                    loadChapter(chapters[index + 1].id)
                }
            }

            ChapterReaderAction.OnPrevChapterClicked -> {
                println("ChapterReaderViewModel | OnPrevChapterClicked")
                val index = _state.value.currentChapterIndex
                val chapters = _state.value.chapterList
                if (index > 0) {
                    _state.value = _state.value.copy(currentChapterIndex = index - 1)
                    loadChapter(chapters[index - 1].id)
                }
            }

            is ChapterReaderAction.OnFontSizeChanged -> {
                println("ChapterReaderViewModel | OnFontSizeChanged | Size: ${action.fontSize}")
                _state.value = _state.value.copy(fontSize = action.fontSize)
            }
            is ChapterReaderAction.OnFontSelected -> {
                println("ChapterReaderViewModel | OnFontSelected | Font: ${action.fontFamily}")
                _state.value = _state.value.copy(readerFont = action.fontFamily)
            }
            is ChapterReaderAction.OnThemeSelected -> {
                println("ChapterReaderViewModel | OnThemeSelected | Theme: ${action.theme}")
                _state.value = _state.value.copy(readerTheme = action.theme)
            }

            is ChapterReaderAction.OnWordClicked -> {
                println("ChapterReaderViewModel | OnWordClicked | Searching locally word: \"${action.word}\"")

                // Cancels the previous job if it exists, so that way previously clicked words aren't collected and updated.
                wordCollectJob?.cancel()

                wordCollectJob = viewModelScope.launch {
                    savedWordRepository.getWord(action.word)
                        .collectLatest { word ->
                            println("ChapterReaderViewModel | OnWordClicked | Updating wordClicked state for: \"${action.word}\"")
                            _state.value = _state.value.copy(
                                wordClicked = word,
                                showModalBottomWord = true,
                                showModalBottomSettings = false,
                                showModalBottomChaptersList = false,
                                translationRelatedImages = if (_state.value.wordClicked == word) _state.value.translationRelatedImages else emptyList(),
                            )
                        }
                }
            }
            ChapterReaderAction.OnDismissWordContentDetail -> {
                println("ChapterReaderViewModel | OnDismissWordContentDetail")
                _state.value = _state.value.copy(
                    showModalBottomWord = false,
                )
            }

            is ChapterReaderAction.OnWordKnowledgeLevelChanged -> {
                val level = KnowledgeLevel.fromId(action.level)

                val word = _state.value.wordClicked?.copy(level = level)
                if (word != null) {
                    println("ChapterReaderViewModel | OnWordKnowledgeLevelClicked | Updating word: \"${word.word}\" to level: ${word.level.name} (${action.level})")
                    viewModelScope.launch {
                        savedWordRepository.addWord(word)
                    }
                }
            }

            ChapterReaderAction.OnReturnClicked -> {
                viewModelScope.launch {
                    _events.send(ChapterReaderEvent.NavigateBack)
                }
            }

            ChapterReaderAction.OnSettingsClicked -> {
                println("ChapterReaderViewModel | OnSettingsClicked")
                _state.value = _state.value.copy(
                    showModalBottomSettings = !_state.value.showModalBottomSettings,
                    showModalBottomChaptersList = false,
                )
            }

            is ChapterReaderAction.OnChapterClicked -> {
                println("ChapterReaderViewModel | OnChapterClicked | ChapterId: ${action.chapterId}")
                _state.value = _state.value.copy(
                    currentChapterIndex = _state.value.chapterList.indexOfFirst { it.id == action.chapterId },
                    showModalBottomChaptersList = false,
                )
                loadChapter(action.chapterId)
            }

            ChapterReaderAction.OnChaptersListClicked -> {
                println("ChapterReaderViewModel | OnChaptersListClicked")
                _state.value = _state.value.copy(
                    showModalBottomChaptersList = true,
                    showModalBottomSettings = false,
                )
            }
            ChapterReaderAction.OnDismissChaptersListClicked -> {
                println("ChapterReaderViewModel | OnDismissChaptersListClicked")
                _state.value = _state.value.copy(showModalBottomChaptersList = false)
            }

            is ChapterReaderAction.OnWordTranslationChanged -> {
                val word = _state.value.wordClicked?.copy(translation = action.translation)
                viewModelScope.launch {
                    println("ChapterReaderViewModel | OnWordTranslationChanged | Updating word: \"${word!!.word}\" to translation: \"${word.translation}\"")
                    savedWordRepository.updateWord(word)

                    println("ChapterReaderViewModel | OnWordTranslationChanged | Searching images for: \"${word.word}\"")
                    _state.value.copy(isLoadingTranslationsImages = true)
                    imageRepository.searchImages(word.translation)
                        .onSuccess { images ->
                            println("ChapterReaderViewModel | OnWordTranslationChanged | Found ${images.size} images for: \"${word.word}\"")
                            _state.value = _state.value.copy(
                                translationRelatedImages = images,
                                isLoadingTranslationsImages = false,
                            )
                        }
                        .onError { error ->
                            println("ChapterReaderViewModel | OnWordTranslationChanged | Error searching images for: \"${word.word}\"\nError: $error")
                            _state.value.copy(isLoadingTranslationsImages = false)
                        }
                }
            }

            is ChapterReaderAction.OnWordImageSelected -> {
                println("ChapterReaderViewModel | OnWordImageSelected | Image: ${action.imageUrl}")
                val word = _state.value.wordClicked?.copy(imageUrl = action.imageUrl)
                _state.value = _state.value.copy(wordClicked = word)
                viewModelScope.launch {
                    savedWordRepository.updateWord(word!!)
                }
            }
        }
    }

    fun loadChapter(chapterId: String) {
        println("ChapterReaderViewModel | loadChapter | Loading chapter: \"$chapterId\"")
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            novelRepository
                .getChapterById(chapterId)
                .onSuccess { chapter ->
                    println("ChapterReaderViewModel | Success getting chapter: \"$chapterId\"")
                    _state.value = _state.value.copy(chapter = chapter, isLoading = false)
                }
                .onError {
                    println("ChapterReaderViewModel | Error getting chapter: \"$chapterId\"")
                    _state.value = _state.value.copy(isLoading = false)
                }
        }
    }
}