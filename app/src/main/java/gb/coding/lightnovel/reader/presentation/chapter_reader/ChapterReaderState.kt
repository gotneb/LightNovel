package gb.coding.lightnovel.reader.presentation.chapter_reader

import gb.coding.lightnovel.reader.domain.models.Chapter

data class ChapterReaderState(
    val chapter: Chapter? = null,
    val isLoading: Boolean = true,
    val showModalBottomChaptersList: Boolean = false,
    val isOverlayVisible: Boolean = false,

    val chapterList: List<Chapter> = emptyList(),
    val currentChapterIndex: Int = -1,
) {
    val isFirstChapter: Boolean
        get() = currentChapterIndex == 0

    val isLastChapter: Boolean
        get() = currentChapterIndex == chapterList.lastIndex
}
