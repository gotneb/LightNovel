package gb.coding.lightnovel.reader.presentation.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.reader.presentation.browse.components.NovelBrowseCard
import gb.coding.lightnovel.reader.presentation.library.components.SearchBar
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun BrowseScreen(
    state: BrowseState,
    onAction: (BrowseAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    var searchText by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        item {
            SearchBar(
                text = searchText,
                onTextChange = { searchText = it },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                placeholder = { Text(text = "Buscar novels...") },
            )
        }

        // Found results
        if (state.searchResults.isNotEmpty()) {
            item {
                Text(
                    text = "Encontrados 4 resultados...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            }

            items(state.searchResults) { novel ->
                NovelBrowseCard(
                    novel = novel,
                    onClick = { onAction(BrowseAction.OnNovelClick(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // No results found
        if (state.searchExecuted && state.searchResults.isEmpty()) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(R.drawable.search_off),
                        contentDescription = null,
                        tint = Color(0xFF625B70),
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(120.dp)
                    )
                    Text(
                        text = "Nenhum resultado encontrado...",
                        color = Color(0xFF625B70),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                    )
                    Text(
                        text = "Talvez essa novel ainda não esteja disponível.",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        // Error
        if (state.error != null) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(R.drawable.error),
                        contentDescription = null,
                        tint = Color(0xFF625B70),
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(120.dp)
                    )
                    Text(
                        text = "Erro inesperado",
                        color = Color(0xFF625B70),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                    )
                    Text(
                        text = "Por favor, tente novamente mais tarde.",
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
private fun BrowseScreenPreview() {
    LightNovelTheme {
        BrowseScreen(
            state = BrowseState(),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}