package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.core.presentation.util.formatDate
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.reader.domain.models.Chapter
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun ChapterListItem(
    chapter: Chapter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .clickable{ onClick() }
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Capítulo ${chapter.chapterNumber}: ${chapter.title}",
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = chapter.createdAt.formatDate(),
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun ChapterListItemPreview() {
    LightNovelTheme {
        ChapterListItem(
            chapter = MockChapters.sample,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}