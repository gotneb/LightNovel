package gb.coding.lightnovel.reader.presentation.novel_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gb.coding.lightnovel.core.domain.model.LanguageCode
import gb.coding.lightnovel.core.domain.util.onError
import gb.coding.lightnovel.core.domain.util.onSuccess
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailEvent.Navigate2ChapterReader
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NovelDetailViewModel(
    private val novelRepository: NovelRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(NovelDetailState())
    val state = _state.asStateFlow()

    private val _events = Channel<NovelDetailEvent>()
    val events = _events.receiveAsFlow()

    init {
        // TODO: Set novelId in its own state, so it's get easy to call helper functions
        val novelId = savedStateHandle.get<String>("novelId")
        println("NovelDetailViewModel | init | novelId: $novelId")

        getNovel(novelId!!)
    }

    fun onAction(action: NovelDetailAction) {
        when (action) {
            is NovelDetailAction.OnChapterClicked -> {
                println("NovelDetailViewModel | onAction | OnChapterClicked")
                viewModelScope.launch {
                    _events.send(Navigate2ChapterReader(action.chapterId))
                }
            }

            NovelDetailAction.OnInvertChaptersListClicked -> {
                println("NovelDetailViewModel | onAction | OnInvertChaptersListClicked")
                _state.update { it.copy(
                    chapters = it.chapters.reversed(),
                    invertList = !it.invertList
                ) }
            }

            // TODO: Implement this
            // We don't have a local database yet, so this is just a placeholder
            NovelDetailAction.OnAdd2LibraryClicked -> {
                println("NovelDetailViewModel | onAction | OnAdd2LibraryClicked")
                _state.update { it.copy(isNovelSaved2Library = true) }
            }
            NovelDetailAction.OnRemoveFromLibraryClicked -> {
                println("NovelDetailViewModel | onAction | OnRemoveFromLibraryClicked")
                _state.update { it.copy(isNovelSaved2Library = false) }
            }
        }
    }

    fun getNovel(id: String) {
        println("NovelDetailViewModel | getNovel | id: $id")
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            novelRepository
                .getNovelById(id)
                .onSuccess { novel ->
                    println("NovelDetailViewModel | getNovel | Success")
                    _state.update { it.copy(novel = novel) }

                    // TODO: Is it allowed in MVI? Those calls seems to be invalid with the pattern... Need to check out it later!
                    getNovelChapters(id)
                    getNovelTags(id, novel.language)
                }
                .onError { error ->
                    println("NovelDetailViewModel | getNovel | Error: $error")
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }

    fun getNovelChapters(id: String) {
        println("NovelDetailViewModel | getNovelChapters | Novel id: \"$id\"")
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            novelRepository
                .getChaptersByNovelId(id)
                .onSuccess { chapters ->
                    println("NovelDetailViewModel | getNovelChapters | Success")
                    _state.update { it.copy(isLoading = false, chapters = chapters) }
                }
                .onError { error ->
                    println("NovelDetailViewModel | getNovelChapters | Error: $error")
                    _state.update { it.copy(isLoading = false) }
                }

        }
    }

    fun getNovelTags(id: String, language: LanguageCode) {
        println("NovelDetailViewModel | getNovelTags | Searching for \"${language.code}\" tags...")
        viewModelScope.launch {
            novelRepository
                // TODO: Get language from novel
                .getTagsByNovelId(id, language)
                .onSuccess { tags ->
                    println("NovelDetailViewModel | getNovelTags | Success")
                    _state.update { it.copy(tags = tags) }
                }
                .onError { error ->
                    println("NovelDetailViewModel | getNovelTags | Error: $error")
                }
        }
    }
}