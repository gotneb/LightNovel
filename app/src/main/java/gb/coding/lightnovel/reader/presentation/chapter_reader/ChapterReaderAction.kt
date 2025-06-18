package gb.coding.lightnovel.reader.presentation.chapter_reader

import gb.coding.lightnovel.core.domain.model.ReaderFont
import gb.coding.lightnovel.core.domain.model.ReaderTheme

sealed interface ChapterReaderAction {
    data object OnScreenClicked: ChapterReaderAction

    data object OnReturnClicked: ChapterReaderAction
    data object OnPrevChapterClicked: ChapterReaderAction
    data object OnNextChapterClicked: ChapterReaderAction

    data class OnFontSizeChanged(val fontSize: Float): ChapterReaderAction
    data class OnFontSelected(val fontFamily: ReaderFont): ChapterReaderAction
    data class OnThemeSelected(val theme: ReaderTheme): ChapterReaderAction

    data object OnSettingsClicked: ChapterReaderAction

    data class OnChapterClicked(val chapterId: String): ChapterReaderAction

    data object OnChaptersListClicked: ChapterReaderAction
    data object OnDismissChaptersListClicked: ChapterReaderAction
}