@file:OptIn(ExperimentalCoroutinesApi::class)

package gb.coding.lightnovel.reader.data.repository

import gb.coding.lightnovel.core.domain.mapper.toSavedWord
import gb.coding.lightnovel.core.domain.mapper.toWordKnowledge
import gb.coding.lightnovel.core.domain.model.KnowledgeLevel
import gb.coding.lightnovel.core.domain.model.WordKnowledge
import gb.coding.lightnovel.reader.data.local.SavedWordDao
import gb.coding.lightnovel.reader.domain.repository.SavedWordRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class SavedWordRepositoryImpl(
    private val savedWordDao: SavedWordDao
) : SavedWordRepository {
    override suspend fun addWord(word: WordKnowledge) {
        println("SavedWordRepositoryImpl | Adding word \"${word.word}\" to saved words. (\"${word.language}\")")
        savedWordDao.upsertWord(word.toSavedWord())
    }

    override suspend fun getWord(word: String): Flow<WordKnowledge> {
        println("SavedWordRepositoryImpl | Getting word \"$word\" from saved words.")
        return savedWordDao.getWord(word).mapLatest { savedWord ->
            savedWord?.toWordKnowledge() ?: WordKnowledge(
                word = word,
                level = KnowledgeLevel.NEW,
                lastUpdated = System.currentTimeMillis(),
                language = "en",
                translation = "",
                imageUrl = "",
            )
        }
    }

    override fun getAllWords(): Flow<List<WordKnowledge>> {
        println("SavedWordRepositoryImpl | Getting all saved words.")
        return savedWordDao.getAllWords().mapLatest { savedWords ->
            savedWords.map { it.toWordKnowledge() }
        }
    }
}