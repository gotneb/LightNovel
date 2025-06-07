package gb.coding.lightnovel.reader.presentation.browse

import gb.coding.lightnovel.reader.domain.models.Novel

sealed interface BrowseEvent {
    data class Navigate2NovelDetail(val id: String) : BrowseEvent
    // Here I can show Snackbar, ShowErrorToast, etc.
}