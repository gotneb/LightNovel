package gb.coding.lightnovel.core.domain.data.networking

import gb.coding.lightnovel.BuildConfig

fun constructUrl(url: String): String {
    return when {
        url.contains(BuildConfig.UNSPLASH_BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.UNSPLASH_BASE_URL + url.drop(1)
        else -> BuildConfig.UNSPLASH_BASE_URL + url
    }
}