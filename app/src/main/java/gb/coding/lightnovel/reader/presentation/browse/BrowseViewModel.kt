package gb.coding.lightnovel.reader.presentation.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gb.coding.lightnovel.core.domain.util.onError
import gb.coding.lightnovel.core.domain.util.onSuccess
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrowseViewModel(
    private val novelRepository: NovelRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BrowseState())
    val state = _state.asStateFlow()

    private val _events = Channel<BrowseEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: BrowseAction) {
        when (action) {
            is BrowseAction.OnNovelClick -> {
                viewModelScope.launch {
                    println("BrowseViewModel | onAction | OnNovelClick | Novel id: ${action.id}")
                    _events.send(BrowseEvent.Navigate2NovelDetail(action.id))
                }
            }

            is BrowseAction.OnQueryChange -> {
                _state.update { it.copy(searchText = action.query) }
            }

            BrowseAction.OnSearchClick -> {
                _state.update { it.copy(searchExecuted = true, isLoading = true) }

                viewModelScope.launch {
                    novelRepository
                        .searchNovels(_state.value.searchText)
                        .onSuccess { novels ->
                            novels.forEach { novel ->
                                println("BrowseViewModel | onAction | searchNovels | id: \"${novel.id}\" | Title: \"${novel.title}\"")
                            }
                            _state.update { it.copy(isLoading = false, searchResults = novels) }
                        }
                        .onError {
                            println("BrowseViewModel | onAction | Error: $it")
                            _state.update {
                                it.copy(isLoading = false, error = "Error happened")
                            }
                        }
                }
            }
        }
    }
}