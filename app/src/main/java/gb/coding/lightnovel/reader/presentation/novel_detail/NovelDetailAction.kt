package gb.coding.lightnovel.reader.presentation.novel_detail

sealed interface NovelDetailAction {
    data class OnChapterClicked(val chapterId: String) : NovelDetailAction
    data object OnInvertChaptersListClicked : NovelDetailAction
}