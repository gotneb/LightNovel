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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderAction
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderState

@Composable
fun ReaderTopBar(
    state: ChapterReaderState,
    onAction: (ChapterReaderAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            IconButton(
                onClick = { onAction(ChapterReaderAction.OnReturnClicked) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "Back",
                )
            }
            Text(
                text = "Capítulo ${state.chapter!!.chapterNumber}: ${state.chapter.title}",
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { onAction(ChapterReaderAction.OnSettingsClicked) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.tune),
                    contentDescription = "Settings",
                )
            }
        }
        HorizontalDivider(thickness = 1.dp)
    }
}