package gb.coding.lightnovel.reader.domain.repository

import gb.coding.lightnovel.core.domain.util.Error
import gb.coding.lightnovel.core.domain.util.Result
import gb.coding.lightnovel.reader.domain.models.Chapter
import gb.coding.lightnovel.reader.domain.models.Novel

interface NovelRepository {
    suspend fun getNovelById(id: String): Result<Novel, Error>
    suspend fun getChapterById(id: String): Result<Chapter, Error>
    suspend fun getChaptersByNovelId(id: String): Result<List<Chapter>, Error>
    suspend fun searchNovels(query: String): Result<List<Novel>, Error>
}