package gb.coding.lightnovel.reader.data.repository.dto

import kotlinx.serialization.Serializable

@Serializable
data class ImageUrlsDto(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

@Serializable
data class ImageDto(
    val id: String,
    val slug: String,
    val urls: ImageUrlsDto,
)
