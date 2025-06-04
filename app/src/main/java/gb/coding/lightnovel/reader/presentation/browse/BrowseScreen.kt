package gb.coding.lightnovel.reader.presentation.browse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun BrowseScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Text("Browse Screen")
    }
}

@Preview
@Composable
private fun BrowseScreenPreview() {
    LightNovelTheme {
        BrowseScreen()
    }
}