@file:OptIn(ExperimentalMaterial3Api::class)

package gb.coding.lightnovel.reader.presentation.chapter_reader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.core.presentation.util.formatDate
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.ChapterListItem
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.ReaderBottomBar
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.ReaderChaptersList
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.ReaderModalSettings
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.ReaderTopBar
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailAction
import gb.coding.lightnovel.ui.theme.LightNovelTheme
import gb.coding.lightnovel.ui.theme.SourceSerif4

@Composable
fun ChapterReaderScreen(
    state: ChapterReaderState,
    onAction: (ChapterReaderAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            CircularProgressIndicator(color = Color(0xFF66558E))
        }
        return
    }

    val sheetState = rememberModalBottomSheetState()

    val scrollState = rememberScrollState()
    val progress by remember {
        derivedStateOf {
            val max = scrollState.maxValue
            if (max == 0) 0f else scrollState.value / max.toFloat()
        }
    }

    Box(
        modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = { onAction(ChapterReaderAction.OnScreenClicked) }
            )
        }
    ) {
        // Content
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

        // Reading progress bar
        Box(
            Modifier
                .fillMaxWidth(progress)
                .height(4.dp)
                .background(Color(0xFF66558E))
        )

        // Top overlay
        AnimatedVisibility(
            visible = state.isOverlayVisible,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            ReaderTopBar(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            )
        }

        // Bottom overlay
        AnimatedVisibility(
            visible = state.isOverlayVisible,
            enter = expandVertically(expandFrom = Alignment.Bottom),
            exit = shrinkVertically(shrinkTowards = Alignment.Bottom),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            ReaderBottomBar(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }

    if (state.showModalBottomChaptersList) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { onAction(ChapterReaderAction.OnDismissChaptersListClicked) }
        ) {
            ReaderChaptersList(
                state = state,
                onChapterClick = { chapterId -> onAction(ChapterReaderAction.OnChapterClicked(chapterId)) }
            )
        }
    }

    if (state.showModalBottomSettings) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { onAction(ChapterReaderAction.OnSettingsClicked) }
        ) {
            ReaderModalSettings(
                onFontSizeChange = {},
                onFontSelected = {},
                onThemeSelected = {},
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ChapterReaderScreenPreview() {
    LightNovelTheme {
        ChapterReaderScreen(
            state = ChapterReaderState(
                isLoading = false,
                isOverlayVisible = true,
                chapter = MockChapters.sample,
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}