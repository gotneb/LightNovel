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
        val novelId = savedStateHandle.get<String>("novelId")
        println("NovelDetailViewModel | init | novelId: $novelId")

        getNovel(novelId!!)
    }

    fun getNovel(id: String) {
        println("NovelDetailViewModel | getNovel | id: $id")

        viewModelScope.launch {
            novelRepository
                .getNovelById(id)
                .onSuccess { novel ->
                    println("NovelDetailViewModel | getNovel | Success")
                    _state.update { it.copy(isLoading = false, novel = novel) }
                }
                .onError { error ->
                    println("NovelDetailViewModel | getNovel | Error: $error")
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }
}