package gb.coding.lightnovel.reader.domain.models

data class Novel(
    val id: String,
    val title: String,
    val author: String,
    val coverImage: String,
    val status: String,
    val description: String,
    val tags: List<String>,
    val chapters: List<Chapter>
)
