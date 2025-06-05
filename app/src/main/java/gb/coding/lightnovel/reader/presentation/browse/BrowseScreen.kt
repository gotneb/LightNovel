package gb.coding.lightnovel.reader.presentation.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.reader.data.mock.MockNovels
import gb.coding.lightnovel.reader.presentation.browse.components.NovelBrowseCard
import gb.coding.lightnovel.reader.presentation.library.components.SearchBar
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun BrowseScreen(
    onAction: (BrowseAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            SearchBar(
                text = searchText,
                onTextChange = { searchText = it },
            )
        }
        item {
            Text(
                text = "Encontrados 4 resultados...",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        }
        items(10) {
            NovelBrowseCard(
                novel = MockNovels.novel,
                onClick = { onAction(BrowseAction.OnNovelClick(it)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun BrowseScreenPreview() {
    LightNovelTheme {
        BrowseScreen(
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}