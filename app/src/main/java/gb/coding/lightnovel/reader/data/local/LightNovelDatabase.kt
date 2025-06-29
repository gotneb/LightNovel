package gb.coding.lightnovel.reader.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BookmarkedNovel::class],
    version = 1,
    exportSchema = false,
)
abstract class LightNovelDatabase: RoomDatabase() {

    abstract val bookmarkedNovelDao: BookmarkedNovelDao
}