package gb.coding.lightnovel.reader.presentation.browse

import gb.coding.lightnovel.reader.domain.models.Novel

sealed interface BrowseAction {
    data class OnNovelClick(val novel: Novel) : BrowseAction
    data class OnQueryChange(val query: String) : BrowseAction
    data object OnSearchClick : BrowseAction
}