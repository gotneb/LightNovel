package gb.coding.lightnovel.reader.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gb.coding.lightnovel.reader.domain.repository.BookmarkedNovelRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val bookmarkedNovelRepository: BookmarkedNovelRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    private val _events = Channel<LibraryEvent>()
    val events = _events.receiveAsFlow()

    init {
        println("LibraryViewModel init")
        viewModelScope.launch {
            bookmarkedNovelRepository.getAllNovels()
                .collect { bookmarkedNovels ->
                    _state.update {
                        it.copy(
                            novels = bookmarkedNovels
                        )
                    }
                }
        }
    }

    fun onAction(action: LibraryAction) {
        when (action) {
            is LibraryAction.OnNovelClicked -> {
                println("LibraryViewModel | OnNovelClicked | Clicked on novel \"${action.novel.title}\" - ${action.novel.id}")
                viewModelScope.launch {
                    _events.send(LibraryEvent.Navigate2NovelDetail(action.novel.id))
                }
            }
        }
    }
}