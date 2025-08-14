package gb.coding.lightnovel.reader.data.repository

import gb.coding.lightnovel.BuildConfig
import gb.coding.lightnovel.core.domain.data.networking.constructUrl
import gb.coding.lightnovel.core.domain.data.networking.safeCall
import gb.coding.lightnovel.core.domain.util.Error
import gb.coding.lightnovel.core.domain.util.Result
import gb.coding.lightnovel.core.domain.util.map
import gb.coding.lightnovel.reader.data.repository.dto.ImageSearchResultsDto
import gb.coding.lightnovel.reader.domain.repository.ImageRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class UnsplashRepositoryImpl(
    private val httpClient: HttpClient,
) : ImageRepository {
    override suspend fun searchImages(query: String): Result<List<String>, Error> {
        println("UnsplashRepositoryImpl | searchImages | Query: \"$query\"")
        return safeCall<ImageSearchResultsDto> {
            httpClient.get(urlString = constructUrl("${BuildConfig.UNSPLASH_BASE_URL}/search/photos?client_id${BuildConfig.UNSPLASH_API_KEY}&query=$query"))
        }.map { response ->
            response.results.map { it.urls.regular }
        }
    }
}