package gb.coding.lightnovel.reader.domain.repository
import gb.coding.lightnovel.core.domain.util.Error
import gb.coding.lightnovel.core.domain.util.Result

interface ImageRepository {

    suspend fun searchImages(query: String): Result<List<String>, Error>
}