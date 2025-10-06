package gb.coding.lightnovel.reader.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_novels")
data class BookmarkedNovel(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    val languageCode: String,
    val coverImage: String
)
