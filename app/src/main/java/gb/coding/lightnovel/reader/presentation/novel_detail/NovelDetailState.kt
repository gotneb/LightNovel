package gb.coding.lightnovel.reader.presentation.novel_detail

import gb.coding.lightnovel.reader.domain.models.Chapter
import gb.coding.lightnovel.reader.domain.models.Novel

data class NovelDetailState(
    val novel: Novel? = null,
    val chapters: List<Chapter> = emptyList(),
    val tags: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val invertList: Boolean = false,
    // val error: String? = null,
)
