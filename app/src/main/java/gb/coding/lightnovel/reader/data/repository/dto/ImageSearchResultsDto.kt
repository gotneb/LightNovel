package gb.coding.lightnovel.reader.data.repository.dto

import kotlinx.serialization.Serializable

@Serializable
data class ImageSearchResultsDto(
    val results: List<ImageDto>,
)