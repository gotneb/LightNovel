package gb.coding.lightnovel.reader.data.repository

import gb.coding.lightnovel.core.domain.util.Error
import gb.coding.lightnovel.core.domain.util.Result
import gb.coding.lightnovel.reader.data.mock.MockNovels
import gb.coding.lightnovel.reader.domain.models.Chapter
import gb.coding.lightnovel.reader.domain.models.Novel
import gb.coding.lightnovel.reader.domain.repository.NovelRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class NovelRepositoryImpl(
    private val supabase: SupabaseClient
) : NovelRepository {
    override suspend fun getNovelById(id: String): Result<Novel, Error> {
        // Todo: implement this
        // It just a wrap for testing while developing the browse navigation to novel detail
        println("NovelRepositoryImpl | getNovelById | id: \"$id\"")
        return Result.Success(MockNovels.sample)
    }

    override suspend fun getChapterById(id: String): Result<Chapter, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun searchNovels(query: String): Result<List<Novel>, Error> {
        println("NovelRepositoryImpl | searchNovels | query: \"$query\"")

        val novels = supabase
            .from("novel")
            .select() {
                filter {
                    ilike("title", "%$query%")
                }
            }
            .decodeList<Novel>()

        return Result.Success(novels)
    }
}