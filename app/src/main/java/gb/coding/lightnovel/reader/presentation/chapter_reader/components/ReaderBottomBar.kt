package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderAction

@Composable
fun ReaderBottomBar(
    onAction: (ChapterReaderAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        HorizontalDivider(thickness = 1.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            IconButton(
                onClick = { onAction(ChapterReaderAction.OnPrevChapterClicked) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = { onAction(ChapterReaderAction.OnChaptersListClicked) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.list_bullet),
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = { onAction(ChapterReaderAction.OnNextChapterClicked) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_forward),
                    contentDescription = null,
                )
            }
        }
    }
}