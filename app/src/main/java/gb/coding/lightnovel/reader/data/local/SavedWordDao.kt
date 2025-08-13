package gb.coding.lightnovel.reader.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedWordDao {
    @Upsert
    suspend fun upsertWord(word: SavedWord)

    @Query("SELECT * FROM saved_words WHERE word = :word")
    fun getWord(word: String): Flow<SavedWord?>

    @Query("SELECT * FROM saved_words")
    fun getAllWords(): Flow<List<SavedWord>>

}