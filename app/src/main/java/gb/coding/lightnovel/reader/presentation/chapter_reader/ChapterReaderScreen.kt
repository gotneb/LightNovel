@file:OptIn(ExperimentalMaterial3Api::class)

package gb.coding.lightnovel.reader.presentation.chapter_reader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.core.domain.mapper.toFontFamily
import gb.coding.lightnovel.core.domain.model.KnowledgeLevel
import gb.coding.lightnovel.core.domain.model.ReaderTheme
import gb.coding.lightnovel.core.domain.model.WordKnowledge
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.ReaderBottomBar
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.ReaderChaptersList
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.ReaderModalSettings
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.ReaderTopBar
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.WordDetailContent
import gb.coding.lightnovel.reader.presentation.chapter_reader.components.WordHighlightText
import gb.coding.lightnovel.ui.theme.LightNovelTheme

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

    // FIX: Serpia theme doesn't apply properly
    val backgroundColor = when (state.readerTheme) {
        ReaderTheme.Light, ReaderTheme.Dark -> MaterialTheme.colorScheme.background
        ReaderTheme.Serpia -> Color(0xFFBFB7A0)
    }

    val textColor = when (state.readerTheme) {
        ReaderTheme.Light, ReaderTheme.Dark -> MaterialTheme.colorScheme.onBackground
        ReaderTheme.Serpia -> Color(0xFF302E33)
    }

    Box(
        modifier = modifier
            .background(backgroundColor)
            .pointerInput(Unit) {
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
                    color = textColor,
                    textAlign = TextAlign.Center,
                    fontSize = (state.fontSize + 2).sp,
                    letterSpacing = 1.4.sp,
                    fontFamily = state.readerFont.toFontFamily(),
                    modifier = Modifier
                        .padding(top = 56.dp)
                        .fillMaxWidth(),
                )
            }
            Text(
                text = "Capítulo ${state.chapter.chapterNumber}:\n${state.chapter.title}",
                color = textColor,
                fontSize = (state.fontSize + 4).sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.4.sp,
                fontFamily = state.readerFont.toFontFamily(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 32.dp)
            )

            WordHighlightText(
                fullText = state.chapter.content,
                color = textColor,
                onScreenClick = { onAction(ChapterReaderAction.OnScreenClicked) },
                onWordClick = { onAction(ChapterReaderAction.OnWordClicked(it)) },
                highlightWords = state.savedWords,
                fontSize = state.fontSize.sp,
                letterSpacing = 1.4.sp,
                fontFamily = state.readerFont.toFontFamily(),
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
                onChapterClick = { chapterId ->
                    onAction(
                        ChapterReaderAction.OnChapterClicked(
                            chapterId
                        )
                    )
                }
            )
        }
    }

    if (state.showModalBottomSettings) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { onAction(ChapterReaderAction.OnSettingsClicked) }
        ) {
            ReaderModalSettings(
                fontSizeValue = state.fontSize,
                fontSelected = state.readerFont,
                modifier = Modifier.padding(horizontal = 16.dp),
                onFontSizeChange = { onAction(ChapterReaderAction.OnFontSizeChanged(it)) },
                onFontSelected = { onAction(ChapterReaderAction.OnFontSelected(it)) },
                onThemeSelected = { onAction(ChapterReaderAction.OnThemeSelected(it)) }
            )
        }
    }

    // TODO: There must be a better way to do this
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxHeight = screenHeight * 0.8f

    if (state.showModalBottomWord) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { onAction(ChapterReaderAction.OnDismissWordContentDetail) }
        ) {
            WordDetailContent(
                word = state.wordClicked,
                onWordLevelChanged = { onAction(ChapterReaderAction.OnWordKnowledgeLevelChanged(it)) },
                modifier = Modifier
                    .padding(8.dp)
                    .heightIn(maxHeight)
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
                isOverlayVisible = false,
                chapter = MockChapters.sample,
                readerTheme = ReaderTheme.Light
            ),
            onAction = {},
        )
    }
}