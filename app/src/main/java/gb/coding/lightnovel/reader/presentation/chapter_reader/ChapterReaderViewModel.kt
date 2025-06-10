package gb.coding.lightnovel.reader.presentation.chapter_reader

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        val chapterId: String = checkNotNull(savedStateHandle["chapterId"])
        viewModelScope.launch {
            novelRepository
                .getChapterById(chapterId)
                .onSuccess { chapter ->
                    println("ChapterReaderViewModel | Success getting chapter \"$chapterId\"")
                    _state.value = _state.value.copy(chapter = chapter, isLoading = false)
                }
                .onError { error ->
                    println("ChapterReaderViewModel | Error getting chapter \"$chapterId\"\nError: $error")
                    _state.value = _state.value.copy(isLoading = false)
                }
        }
    }
}