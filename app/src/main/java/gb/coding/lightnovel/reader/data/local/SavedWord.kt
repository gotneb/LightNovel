package gb.coding.lightnovel.reader.data.local

import androidx.room.Entity
import gb.coding.lightnovel.core.domain.model.KnowledgeLevel

@Entity(
    tableName = "saved_words",
    // It will guarantees there'll be only one row per word and language.
    primaryKeys = ["word", "language"]
)
data class SavedWord(
    var word: String = "",
    var translation: String = "",
    var imageUrl: String = "",
    var language: String = "",
    var level: Int = KnowledgeLevel.NEW.ordinal,
    var createdAtd: Long = System.currentTimeMillis(),
    var lastUpdated: Long = System.currentTimeMillis()
)