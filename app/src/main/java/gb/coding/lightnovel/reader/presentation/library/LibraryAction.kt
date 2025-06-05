package gb.coding.lightnovel.reader.presentation.library

import gb.coding.lightnovel.reader.domain.models.Novel

sealed interface LibraryAction {
    data class OnNovelClicked(val novel: Novel) : LibraryAction
}