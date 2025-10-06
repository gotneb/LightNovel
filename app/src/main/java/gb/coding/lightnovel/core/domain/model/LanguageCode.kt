package gb.coding.lightnovel.core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class LanguageCode(val code: String) {
    @SerialName("en")
    ENGLISH("en"),

    @SerialName("de")
    GERMAN("de"),

    @SerialName("ja")
    JAPANESE("ja"),

    @SerialName("pt-BR")
    BRAZILIAN_PORTUGUESE("pt-BR"),
}