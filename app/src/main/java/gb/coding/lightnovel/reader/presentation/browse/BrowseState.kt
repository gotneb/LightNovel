package gb.coding.lightnovel.reader.presentation.browse

import gb.coding.lightnovel.reader.domain.models.Novel

data class BrowseState(
    val searchResults: List<Novel> = emptyList(),
    val searchText: String = "",
    val error: String? = null,
    val searchExecuted: Boolean = false,
    val isLoading: Boolean = false,
)
