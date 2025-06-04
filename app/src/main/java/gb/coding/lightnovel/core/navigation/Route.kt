package gb.coding.lightnovel.core.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    data object NovelDetail : Route()

    @Serializable
    data object ChapterReader : Route()
}