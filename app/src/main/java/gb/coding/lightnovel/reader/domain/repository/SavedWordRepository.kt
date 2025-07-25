package gb.coding.lightnovel.reader.domain.repository

import gb.coding.lightnovel.core.domain.model.WordKnowledge
import kotlinx.coroutines.flow.Flow

interface SavedWordRepository {
    suspend fun addWord(word: WordKnowledge)

    suspend fun getWord(word: String): Flow<WordKnowledge>
}