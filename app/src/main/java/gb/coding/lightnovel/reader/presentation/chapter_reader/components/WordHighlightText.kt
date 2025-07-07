package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
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
import gb.coding.lightnovel.ui.theme.LightNovelTheme
import kotlin.text.Regex

@Composable
fun WordHighlightText(
    fullText: String,
    color: Color = Color.Unspecified,
    highlightWords: List<WordKnowledge>,
    onWordClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    // Map word to level for fast lookup
    val wordToLevel = highlightWords.associateBy { it.word }

    val annotatedString = buildAnnotatedString {
        val wordRegex = Regex("""\b\p{L}+\b""")
        var lastIndex = 0

        for (match in wordRegex.findAll(fullText)) {
            val word = match.value
            val start = match.range.first
            val end = match.range.last + 1

            if (start > lastIndex) {
                append(fullText.substring(lastIndex, start))
            }

            // Lookup level, default to NEW
            val knowledgeLevel = wordToLevel[word]?.level ?: KnowledgeLevel.NEW

            pushStringAnnotation(tag = "WORD", annotation = word)

            val bgColor = knowledgeLevel.getBackgroundColor()
            val textColor = knowledgeLevel.getTextColor()

            if (bgColor != null) {
                withStyle(SpanStyle(background = bgColor, color = textColor)) {
                    append(word)
                }
            } else {
                // No background, default color
                append(word)
            }
            pop()

            lastIndex = end
        }

        if (lastIndex < fullText.length) {
            append(fullText.substring(lastIndex))
        }
    }

    ClickableText(
        text = annotatedString,
        modifier = modifier,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            letterSpacing = letterSpacing,
            fontFamily = fontFamily,
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations("WORD", offset, offset)
                .firstOrNull()?.let { annotation ->
                    onWordClick(annotation.item)
                }
        },
    )
}


@Preview(showBackground = true)
@Composable
private fun UnderlineColoredTextPreview() {
    LightNovelTheme {
        val text = """
        Essas palavras entraram nos ouvidos de Wang Lin e fez seus olhos ficarem sérios. 
        
        Uma grande quantidade de energia espiritual foi transferida dos dez fragmentos da alma para Wang Lin.
    """.trimIndent()

        val highlights = mapOf(
            "Wang" to Color(0xFFD1C4E9),     // purple
            "palavras" to Color(0xFFF8BBD0), // pink
            "de" to Color(0xFFB2EBF2),       // teal
            "grande" to Color(0xFFC8E6C9),   // light green
            "dez" to Color(0xFFFFF59D)       // yellow
        )

        WordHighlightText(
            fullText = text,
            onWordClick = {},
            highlightWords = emptyList(),
        )
    }
}