package gb.coding.lightnovel.reader.presentation.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun LibraryScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Text("Library Screen")
    }
}

@PreviewLightDark
@Composable
private fun LibraryScreenPreview() {
    LightNovelTheme {
        LibraryScreen(

        )
    }
}