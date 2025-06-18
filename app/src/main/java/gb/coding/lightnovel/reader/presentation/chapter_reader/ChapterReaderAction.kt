package gb.coding.lightnovel.reader.presentation.chapter_reader

sealed interface ChapterReaderAction {
    data object OnScreenClicked: ChapterReaderAction

    data object OnReturnClicked: ChapterReaderAction
    data object OnPrevChapterClicked: ChapterReaderAction
    data object OnNextChapterClicked: ChapterReaderAction

    data class OnFontSizeChanged(val fontSize: Float): ChapterReaderAction

    data object OnSettingsClicked: ChapterReaderAction

    data class OnChapterClicked(val chapterId: String): ChapterReaderAction

    data object OnChaptersListClicked: ChapterReaderAction
    data object OnDismissChaptersListClicked: ChapterReaderAction
}