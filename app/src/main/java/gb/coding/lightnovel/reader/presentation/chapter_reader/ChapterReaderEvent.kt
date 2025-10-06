package gb.coding.lightnovel.reader.presentation.chapter_reader

sealed interface ChapterReaderEvent {
    data object NavigateBack: ChapterReaderEvent
}