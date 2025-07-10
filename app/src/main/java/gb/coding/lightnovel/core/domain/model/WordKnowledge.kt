package gb.coding.lightnovel.core.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class WordKnowledge(
    val word: String,
    val level: KnowledgeLevel,
    val lastUpdated: Long,      // Timestamp in millis (for stats/sync)
    val language: String,       // "de", "pt", etc., in case you add multilingual support
)
