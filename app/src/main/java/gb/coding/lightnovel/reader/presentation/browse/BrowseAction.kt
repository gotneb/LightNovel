package gb.coding.lightnovel.reader.presentation.browse

sealed interface BrowseAction {
    data class OnNovelClick(val id: String) : BrowseAction
    data class OnQueryChange(val query: String) : BrowseAction
    data object OnSearchClick : BrowseAction
}