package gb.coding.lightnovel.core.navigation

import kotlinx.serialization.Serializable

sealed class Route() {
    @Serializable
    data object Library : Route()

    @Serializable
    data object Browse : Route()

    @Serializable
    data class NovelDetail(val novelId: String) : Route()

    @Serializable
    data class ChapterReader(val chapterId: String) : Route()
}