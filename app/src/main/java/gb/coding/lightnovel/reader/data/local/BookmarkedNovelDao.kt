package gb.coding.lightnovel.reader.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkedNovelDao {

    @Upsert
    suspend fun upsertNovel(novel: BookmarkedNovel)

    @Delete
    suspend fun deleteNovel(novel: BookmarkedNovel)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_novels WHERE id = :id)")
    fun isBookmarked(id: String): Flow<Boolean>

    @Query("SELECT * FROM bookmarked_novels")
    fun getAllNovels(): Flow<List<BookmarkedNovel>>
}