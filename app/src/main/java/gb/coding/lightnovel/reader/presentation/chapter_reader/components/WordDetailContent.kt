package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.core.domain.model.KnowledgeLevel
import gb.coding.lightnovel.core.domain.model.LanguageCode
import gb.coding.lightnovel.core.domain.model.WordKnowledge
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun WordDetailContent(
    word: WordKnowledge?,
    onWordLevelChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Just for demo purposes
    var translationText by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = word?.word ?: "Word",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            HorizontalDivider()
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Translation",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
            TextField(
                value = translationText,
                onValueChange = { translationText = it },
                placeholder = { Text(text = "Translation...") },
                colors = TextFieldDefaults.colors(
                    // I can edit background here...
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        KnowledgeLevelSelector(
            selectedLevel = word?.level?.id ?: 1,
            onLevelSelected = { level ->
                onWordLevelChanged(level)
            }
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Image",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
            SearchImagePlaceholder()
        }
    }
}

internal val wordKnowledgePreview = WordKnowledge(
    word = "Zimmer",
    translation = "Quarto",
    imageUrl = "",
    level = KnowledgeLevel.IGNORE,
    lastUpdated = System.currentTimeMillis(),
    language = LanguageCode.GERMAN.code,
)

@Preview(showBackground = true)
@Composable
private fun WordDetailContentPreview() {
    LightNovelTheme {
        WordDetailContent(
            word = wordKnowledgePreview,
            onWordLevelChanged = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}