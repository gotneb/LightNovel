package gb.coding.lightnovel.reader.presentation.chapter_reader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.ui.theme.LightNovelTheme
import gb.coding.lightnovel.ui.theme.SourceSerif4

@Composable
fun ChapterReaderScreen(
    state: ChapterReaderState,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            CircularProgressIndicator(
                color = Color(0xFF66558E)
            )
        }
        return
    }

    val scrollState = rememberScrollState()

    val progress by remember {
        derivedStateOf {
            val max = scrollState.maxValue
            if (max == 0) 0f else scrollState.value / max.toFloat()
        }
    }

    Box(modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp),
        ) {
            state.chapter!!.part?.let { part ->
                Text(
                    text = part,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    letterSpacing = 1.4.sp,
                    fontFamily = SourceSerif4,
                    modifier = Modifier
                        .padding(top = 56.dp)
                        .fillMaxWidth(),
                )
            }
            Text(
                text = "Capítulo ${state.chapter.chapterNumber}:\n${state.chapter.title}",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.4.sp,
                fontFamily = SourceSerif4,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 32.dp)
            )


            Text(
                text = state.chapter.content,
                fontSize = 16.sp,
                letterSpacing = 1.4.sp,
                fontFamily = SourceSerif4,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Box(
            Modifier
                .fillMaxWidth(progress)
                .height(4.dp)
                .background(Color(0xFF66558E))
        )
    }
}

@PreviewLightDark
@Composable
private fun ChapterReaderScreenPreview() {
    LightNovelTheme {
        ChapterReaderScreen(
            state = ChapterReaderState(
                isLoading = false,
                chapter = MockChapters.sample,
            ),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        )
    }
}