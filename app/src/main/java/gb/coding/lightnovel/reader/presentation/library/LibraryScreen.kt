package gb.coding.lightnovel.reader.presentation.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.reader.data.mock.MockNovels
import gb.coding.lightnovel.reader.domain.models.Novel
import gb.coding.lightnovel.reader.presentation.library.components.SearchBar
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun LibraryScreen(
    novels: List<Novel>,
    onAction: (LibraryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by rememberSaveable { mutableStateOf("") }

    val numberColumns = 3

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        item(span = { GridItemSpan(numberColumns) }) {
            Text(
                text = "Biblioteca",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        item(span = { GridItemSpan(numberColumns) }) {
            SearchBar(
                text = searchText,
                onTextChange = { searchText = it },
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
            )
        }
        items(10) {
            Column(Modifier.clickable{
                // TODO: Implement with actual data
                onAction(LibraryAction.OnNovelClicked(MockNovels.novel))
            }) {
                Image(
                    painter = painterResource(R.drawable.image_placeholder),
                    contentDescription = null,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                )
                Text(
                    text = "Renegade Immortal",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LibraryScreenPreview() {
    LightNovelTheme {
        LibraryScreen(
            novels = emptyList(),
            onAction = {},
        )
    }
}