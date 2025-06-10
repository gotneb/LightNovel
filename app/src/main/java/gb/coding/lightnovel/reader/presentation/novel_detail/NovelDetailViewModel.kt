package gb.coding.lightnovel.reader.presentation.novel_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gb.coding.lightnovel.core.domain.util.onError
import gb.coding.lightnovel.core.domain.util.onSuccess
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NovelDetailViewModel(
    private val novelRepository: NovelRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(NovelDetailState())
    val state = _state.asStateFlow()

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
                TODO()
            }
            NovelDetailAction.OnInvertChaptersListClicked -> {
                println("NovelDetailViewModel | onAction | OnInvertChaptersListClicked")
                _state.update { it.copy(
                    chapters = it.chapters.reversed(),
                    invertList = !it.invertList
                ) }
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

                    getNovelChapters(id)
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
}