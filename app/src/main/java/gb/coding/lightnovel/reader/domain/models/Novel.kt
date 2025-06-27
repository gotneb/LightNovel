package gb.coding.lightnovel.reader.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Novel(
    val id: String,
    val title: String,
    val author: String,
    @SerialName("cover_image_url")
    val coverImage: String,
    val status: String,
    val description: String,
)
