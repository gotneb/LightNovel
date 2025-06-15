package gb.coding.lightnovel.reader.presentation.chapter_reader

sealed interface ChapterReaderAction {
    data object OnScreenClicked: ChapterReaderAction
    data object OnReturnClicked: ChapterReaderAction
    data object OnPrevChapterClicked: ChapterReaderAction
    data object OnNextChapterClicked: ChapterReaderAction
    data object OnSettingsClicked: ChapterReaderAction
    data object OnChaptersListClicked: ChapterReaderAction
}