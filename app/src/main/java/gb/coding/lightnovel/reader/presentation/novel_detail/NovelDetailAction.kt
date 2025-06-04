package gb.coding.lightnovel.reader.presentation.novel_detail

import gb.coding.lightnovel.reader.domain.models.Chapter

sealed interface NovelDetailAction {
    data class OnChapterClicked(val chapter: Chapter) : NovelDetailAction
}