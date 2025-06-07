package gb.coding.lightnovel.reader.presentation.browse

sealed interface BrowseEvent {
    data class Navigate2NovelDetail(val novelId: String) : BrowseEvent
    // Here I can show Snackbar, ShowErrorToast, etc.
}