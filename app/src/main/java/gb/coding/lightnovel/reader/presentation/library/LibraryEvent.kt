package gb.coding.lightnovel.reader.presentation.library

sealed interface LibraryEvent {
    data class Navigate2NovelDetail(val novelId: String): LibraryEvent
}