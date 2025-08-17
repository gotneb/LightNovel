package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import gb.coding.lightnovel.R
import gb.coding.lightnovel.core.domain.model.KnowledgeLevel
import gb.coding.lightnovel.core.domain.model.LanguageCode
import gb.coding.lightnovel.core.domain.model.WordKnowledge
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun WordDetailContent(
    word: WordKnowledge?,
    translationImages: List<String>,
    onTranslationChange: (String) -> Unit,
    onWordLevelChanged: (Int) -> Unit,
    onWordImageSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoadingImages: Boolean = false,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var translation by remember { mutableStateOf(word?.translation ?: "") }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = word?.word ?: "Word",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
            HorizontalDivider()
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Translation",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
            TextField(
                value = translation,
                onValueChange = { translation = it },
                placeholder = { Text(text = "Translation...") },
                colors = TextFieldDefaults.colors(
                    // I can edit background here...
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onTranslationChange(translation)
                        keyboardController?.hide()
                    }
                ),
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
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
            when {
                word?.imageUrl == null || word.translation.isBlank() -> {
                    SearchImagePlaceholder()
                }
                isLoadingImages -> {
                    CircularProgressIndicator()
                }
                word.imageUrl.isNotBlank() -> {
                    AsyncImage(
                        model = word.imageUrl,
                        contentDescription = "Photo of ${word.translation}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .aspectRatio(16f / 9f)
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
//                        items(10) {
//                            AsyncImage(
//                                model = if (LocalInspectionMode.current) R.drawable.image_placeholder else word.imageUrl,
//                                contentDescription = "Photo of ${word.translation}",
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier
//                                    // Standard size of each novel cover
//                                    .aspectRatio(16f / 9f)
//                                    .clip(RoundedCornerShape(8.dp))
//                                    .clickable { onWordImageSelected(word.imageUrl) }
//                            )
//                        }
                        items(translationImages) { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Photo of ${word.translation}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    // Standard size of each novel cover
                                    .aspectRatio(16f / 9f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onWordImageSelected(imageUrl) })
                        }
                    }
                }
            }

        }
    }
}

internal val wordKnowledgePreview = WordKnowledge(
    word = "Zimmer",
    translation = "Quarto",
    imageUrl = "https://lh5.googleusercontent.com/proxy/oZNo7GYC4EEX_t0qFzT9rvVW9fNBxnU761LVlwXE8YtBwhASogidbpg3gfMakNg-Q7eN47w5hwu9FsI",
    level = KnowledgeLevel.IGNORE,
    lastUpdated = System.currentTimeMillis(),
    language = LanguageCode.GERMAN.code,
)

@PreviewLightDark
@Composable
private fun WordDetailContentPreview() {
    LightNovelTheme {
        WordDetailContent(
            word = wordKnowledgePreview,
            translationImages = emptyList(),
            onWordLevelChanged = {},
            onTranslationChange = {},
            onWordImageSelected = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)
        )
    }
}