package gb.coding.lightnovel.reader.presentation.novel_detail

import gb.coding.lightnovel.reader.domain.models.Novel

data class NovelDetailState(
    val novel: Novel? = null,
    val isLoading: Boolean = false,
    // val error: String? = null,
)
