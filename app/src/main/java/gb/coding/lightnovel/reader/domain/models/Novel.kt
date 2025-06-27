package gb.coding.lightnovel.reader.domain.models

import gb.coding.lightnovel.core.domain.model.LanguageCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Novel(
    val id: String,
    @SerialName("language_code")
    val language: LanguageCode,
    val title: String,
    val author: String,
    @SerialName("cover_image_url")
    val coverImage: String,
    val status: String,
    val description: String,
)
