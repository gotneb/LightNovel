package gb.coding.lightnovel.reader.presentation.chapter_reader

import gb.coding.lightnovel.reader.domain.models.Chapter

data class ChapterReaderState(
    val chapter: Chapter? = null,
    val isLoading: Boolean = true,
    val isOverlayVisible: Boolean = false,
    // val error: String? = null
)
