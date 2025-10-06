package gb.coding.lightnovel.reader.domain.repository

import gb.coding.lightnovel.reader.domain.models.Novel
import kotlinx.coroutines.flow.Flow

interface BookmarkedNovelRepository {

    suspend fun addNovel(novel: Novel)

    suspend fun removeNovel(novel: Novel)

    fun isNovelBookmarked(novelId: String): Flow<Boolean>

    fun getAllNovels(): Flow<List<Novel>>
}