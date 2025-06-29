package gb.coding.lightnovel.reader.data.repository

import gb.coding.lightnovel.core.domain.mapper.toBookmarkedNovel
import gb.coding.lightnovel.reader.data.local.BookmarkedNovelDao
import gb.coding.lightnovel.reader.domain.models.Novel
import gb.coding.lightnovel.reader.domain.repository.BookmarkedNovelRepository
import kotlinx.coroutines.flow.Flow

class BookmarkedNovelRepositoryImpl(
    private val bookmarkedNovelDao: BookmarkedNovelDao
): BookmarkedNovelRepository {

    override suspend fun addNovel(novel: Novel) {
        println("BookmarkedNovelRepositoryImpl | Adding novel \"${novel.title}\" to bookmarks")
        bookmarkedNovelDao.upsertNovel(novel.toBookmarkedNovel())
    }

    override suspend fun removeNovel(novel: Novel) {
        println("BookmarkedNovelRepositoryImpl | Removing novel \"${novel.title}\" from bookmarks")
        bookmarkedNovelDao.deleteNovel(novel.toBookmarkedNovel())
    }

    override fun isNovelBookmarked(novelId: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getAllNovels(): Flow<List<Novel>> {
        TODO("Not yet implemented")
    }
}