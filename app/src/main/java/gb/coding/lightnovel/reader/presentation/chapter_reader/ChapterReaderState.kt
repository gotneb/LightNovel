package gb.coding.lightnovel.reader.presentation.chapter_reader

import gb.coding.lightnovel.core.domain.model.ReaderFont
import gb.coding.lightnovel.core.domain.model.ReaderTheme
import gb.coding.lightnovel.reader.domain.models.Chapter

data class ChapterReaderState(
    val chapter: Chapter? = null,
    val isLoading: Boolean = true,
    val isOverlayVisible: Boolean = false,

    val fontSize: Float = 16f,
    val readerFont: ReaderFont = ReaderFont.SourceSerif4,
    val readerTheme: ReaderTheme = ReaderTheme.Light,
    val wordClicked: String = "[NULL]",

    val showModalBottomSettings: Boolean = false,
    val showModalBottomChaptersList: Boolean = false,
    val showModalBottomWord: Boolean = false,

    val chapterList: List<Chapter> = emptyList(),
    val currentChapterIndex: Int = -1,
) {
    val isFirstChapter: Boolean
        get() = currentChapterIndex == 0

    val isLastChapter: Boolean
        get() = currentChapterIndex == chapterList.lastIndex
}
