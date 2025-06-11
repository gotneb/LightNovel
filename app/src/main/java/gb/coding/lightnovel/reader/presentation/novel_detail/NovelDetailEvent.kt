package gb.coding.lightnovel.reader.presentation.novel_detail

sealed interface NovelDetailEvent {
    data class Navigate2ChapterReader(val chapterId: String) : NovelDetailEvent
}