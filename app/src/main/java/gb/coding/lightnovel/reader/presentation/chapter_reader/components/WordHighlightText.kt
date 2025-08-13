package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import gb.coding.lightnovel.core.domain.model.KnowledgeLevel
import gb.coding.lightnovel.core.domain.model.WordKnowledge
import gb.coding.lightnovel.core.domain.model.getBackgroundColor
import gb.coding.lightnovel.core.domain.model.getTextColor
import gb.coding.lightnovel.reader.data.local.SavedWord
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun WordHighlightText(
    fullText: String,
    color: Color = Color.Unspecified,
    highlightWords: List<WordKnowledge>,
    onWordClick: (String) -> Unit,
    onScreenClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    val darkTheme = isSystemInDarkTheme()
    val wordToLevel = highlightWords.associateBy { it.word }

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    val annotatedString = remember(fullText, highlightWords, darkTheme) {
        buildAnnotatedString {
            val wordRegex = Regex("""\b\p{L}+\b""")
            var lastIndex = 0

            for (match in wordRegex.findAll(fullText)) {
                val word = match.value
                val start = match.range.first
                val end = match.range.last + 1

                if (start > lastIndex) {
                    append(fullText.substring(lastIndex, start))
                }

                val knowledgeLevel = wordToLevel[word]?.level ?: KnowledgeLevel.NEW
                val bgColor = knowledgeLevel.getBackgroundColor(darkTheme)
                val textColor = knowledgeLevel.getTextColor(darkTheme)

                pushStringAnnotation(tag = "WORD", annotation = word)

                if (bgColor != null) {
                    withStyle(SpanStyle(background = bgColor, color = textColor)) {
                        append(word)
                    }
                } else {
                    append(word)
                }

                pop()
                lastIndex = end
            }

            if (lastIndex < fullText.length) {
                append(fullText.substring(lastIndex))
            }
        }
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    textLayoutResult?.let { layout ->
                        val position = layout.getOffsetForPosition(offset)
                        val annotation = annotatedString.getStringAnnotations("WORD", position, position).firstOrNull()
                        if (annotation != null) {
                            onWordClick(annotation.item)
                        } else {
                            onScreenClick()
                        }
                    }
                }
            }
    ) {
        Text(
            text = annotatedString,
            style = TextStyle(
                color = color,
                fontSize = fontSize,
                letterSpacing = letterSpacing,
                fontFamily = fontFamily,
            ),
            onTextLayout = { textLayoutResult = it }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun UnderlineColoredTextPreview() {
    LightNovelTheme {
        val text = """
        Essas palavras entraram nos ouvidos de Wang Lin e fez seus olhos ficarem sérios. 
        
        Uma grande quantidade de energia espiritual foi transferida dos dez fragmentos da alma para Wang Lin.
    """.trimIndent()

        WordHighlightText(
            fullText = text,
            onWordClick = {},
            onScreenClick = {},
            highlightWords = emptyList(),
        )
    }
}