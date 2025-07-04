package gb.coding.lightnovel.reader.domain.models

data class Chapter(
    val id: String,
    val part: String, // can be a book, volume, arc, etc...
    val title: String,
    val content: String,
    val chapterNumber: String,
)
