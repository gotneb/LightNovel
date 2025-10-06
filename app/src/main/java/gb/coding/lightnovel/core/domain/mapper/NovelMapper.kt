package gb.coding.lightnovel.core.domain.mapper

import gb.coding.lightnovel.core.domain.model.KnowledgeLevel
import gb.coding.lightnovel.core.domain.model.LanguageCode
import gb.coding.lightnovel.core.domain.model.WordKnowledge
import gb.coding.lightnovel.reader.data.local.BookmarkedNovel
import gb.coding.lightnovel.reader.data.local.SavedWord
import gb.coding.lightnovel.reader.domain.models.Novel

fun BookmarkedNovel.toNovel(): Novel {
    return Novel(
        id = id,
        title = title,
        language = LanguageCode.entries.first { it.code == languageCode },
        coverImage = coverImage,
        // We don't need this information. Leave it blank for now
        author = author,
        status = "",
        description = ""
    )
}

fun Novel.toBookmarkedNovel(): BookmarkedNovel {
    return BookmarkedNovel(
        id = id,
        title = title,
        author = author,
        languageCode = language.code,
        coverImage = coverImage,
    )
}

fun WordKnowledge.toSavedWord(): SavedWord {
    return SavedWord(
        word = word,
        language = language,
        level = level.ordinal,
        translation = translation,
        imageUrl = imageUrl,
        lastUpdated = System.currentTimeMillis(),
    )
}

fun SavedWord.toWordKnowledge(): WordKnowledge {
    return WordKnowledge(
        word = word,
        language = language,
        translation = translation,
        imageUrl = imageUrl,
        level = KnowledgeLevel.fromId(level),
        lastUpdated = System.currentTimeMillis(),
    )
}