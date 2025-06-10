package gb.coding.lightnovel.reader.domain.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Chapter(
    val id: String,
    val part: String?, // can be a book, volume, arc, etc...
    val title: String,
    // TODO: Investigate a better approach, I'm only letting it be empty for now
    // Because I need to avoid data overhead in the chapter list when fetching all chapters
    val content: String = "",
    @SerialName("chapter_number")
    val chapterNumber: String,
    @SerialName("created_at")
    val createdAt: Instant
)
