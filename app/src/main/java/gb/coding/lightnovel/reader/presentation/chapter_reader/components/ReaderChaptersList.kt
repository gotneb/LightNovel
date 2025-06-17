package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderState
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun ReaderChaptersList(
    state: ChapterReaderState,
    onChapterClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(state.chapterList) { chapter ->
            ChapterListItem(
                chapter = chapter,
                onClick = { onChapterClick(chapter.id) },
            )
        }
    }
}

@Preview
@Composable
private fun ModalChaptersListPreview() {
    LightNovelTheme {
        ReaderChaptersList(
            state = ChapterReaderState(
                isLoading = false,
                isOverlayVisible = true,
                chapter = MockChapters.sample,
                chapterList = MockChapters.samples,
            ),
            onChapterClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}