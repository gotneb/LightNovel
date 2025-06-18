package gb.coding.lightnovel.reader.presentation.chapter_reader

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gb.coding.lightnovel.core.domain.util.onError
import gb.coding.lightnovel.core.domain.util.onSuccess
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChapterReaderViewModel(
    savedStateHandle: SavedStateHandle,
    private val novelRepository: NovelRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ChapterReaderState())
    val state = _state.asStateFlow()

    init {
        val chapterId: String = checkNotNull(savedStateHandle["chapterId"]) {
            println("ChapterReaderViewModel | init | Error getting chapterId from savedStateHandle")
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

            ChapterReaderAction.OnReturnClicked -> TODO()

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