package gb.coding.lightnovel.reader.presentation.novel_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gb.coding.lightnovel.core.domain.model.LanguageCode
import gb.coding.lightnovel.core.domain.util.onError
import gb.coding.lightnovel.core.domain.util.onSuccess
import gb.coding.lightnovel.reader.domain.repository.BookmarkedNovelRepository
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
    private val bookmarkedNovelRepository: BookmarkedNovelRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(NovelDetailState())
    val state = _state.asStateFlow()

    private val _events = Channel<NovelDetailEvent>()
    val events = _events.receiveAsFlow()

    init {
        // TODO: Set novelId in its own state, so it's more convenient to call helper functions
        val novelId = savedStateHandle.get<String>("novelId")
        println("NovelDetailViewModel | init | novelId: $novelId")

        /*
         * TODO: When a novel is saved on user's device, we only need to fetch the chapters
         *  and tags of the novel.
         */

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

            NovelDetailAction.OnAdd2LibraryClicked -> {
                println("NovelDetailViewModel | onAction | OnAdd2LibraryClicked")
                viewModelScope.launch {
                    bookmarkedNovelRepository.addNovel(state.value.novel!!)
                    _state.update { it.copy(isNovelSaved2Library = true) }
                }
            }

            NovelDetailAction.OnRemoveFromLibraryClicked -> {
                println("NovelDetailViewModel | onAction | OnRemoveFromLibraryClicked")
                viewModelScope.launch {
                    bookmarkedNovelRepository.removeNovel(state.value.novel!!)
                    _state.update { it.copy(isNovelSaved2Library = false) }
                }
            }
        }
    }

    private fun getNovel(id: String) {
        println("NovelDetailViewModel | getNovel | id: $id")
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            novelRepository
                .getNovelById(id)
                .onSuccess { novel ->
                    println("NovelDetailViewModel | getNovel | Success")
                    _state.update { it.copy(novel = novel) }

                    updateBookmarkStatus(novel.id)

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

    private fun getNovelChapters(id: String) {
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

    private fun getNovelTags(id: String, language: LanguageCode) {
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

    private fun updateBookmarkStatus(novelId: String) {
        viewModelScope.launch {
            bookmarkedNovelRepository
                .isNovelBookmarked(novelId)
                .collect { isBookmarked ->
                    println("NovelDetailViewModel | observeBookmarkStatus | isBookmarked: $isBookmarked")
                    _state.update { it.copy(isNovelSaved2Library = isBookmarked) }
                }
        }
    }
}