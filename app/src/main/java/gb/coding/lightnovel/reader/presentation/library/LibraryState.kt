package gb.coding.lightnovel.reader.presentation.library

import gb.coding.lightnovel.reader.domain.models.Novel

data class LibraryState(
    val novels: List<Novel> = emptyList(),
    val searchText: String = "",
)
