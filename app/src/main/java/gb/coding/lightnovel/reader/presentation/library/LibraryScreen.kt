package gb.coding.lightnovel.reader.presentation.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.reader.data.mock.MockNovels
import gb.coding.lightnovel.reader.domain.models.Novel
import gb.coding.lightnovel.reader.presentation.library.components.LibraryNovelCard
import gb.coding.lightnovel.reader.presentation.library.components.SearchBar
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun LibraryScreen(
    state: LibraryState,
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
        
        if (state.novels.isNotEmpty()) {
            item(span = { GridItemSpan(numberColumns) }) {
                SearchBar(
                    text = searchText,
                    onTextChange = { searchText = it },
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    placeholder = { Text("Pesquisar na biblioteca...") },
                )
            }

            items(state.novels) { novel ->
                LibraryNovelCard(
                    novel = MockNovels.novel,
                    onClick = { onAction(LibraryAction.OnNovelClicked(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            item(span = { GridItemSpan(numberColumns) }) {
                // TODO: Implement a way to always let it centered on the screen
                // I haven't yet figured out a way for this...
                val configuration = LocalConfiguration.current
                val screenHeight = configuration.screenHeightDp.dp
                val paddingTop = with(LocalDensity.current) {
                    (screenHeight * 0.22f).value.dp
                }

                Column(Modifier.fillMaxSize()) {
                    Icon(
                        painter = painterResource(R.drawable.inbox),
                        contentDescription = null,
                        tint = Color(0xFF625B70),
                        modifier = Modifier
                            .padding(top = paddingTop)
                            .fillMaxWidth()
                            .aspectRatio(2f)
                    )
                    Text(
                        text = "Oops... Nada aqui!",
                        color = Color(0xFF625B70),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Assim que uma novel for salva, ela aparecerá aqui.",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LibraryScreenPreview() {
    LightNovelTheme {
        LibraryScreen(
            state = LibraryState(),
            onAction = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}