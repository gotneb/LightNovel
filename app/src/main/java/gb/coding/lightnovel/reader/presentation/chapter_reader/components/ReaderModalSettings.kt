package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.core.domain.model.ReaderFont
import gb.coding.lightnovel.core.domain.model.ReaderTheme
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun ReaderModalSettings(
    fontSizeValue: Float,
    fontSelected: ReaderFont,
    onFontSizeChange: (Float) -> Unit,
    onFontSelected: (ReaderFont) -> Unit,
    onThemeSelected: (ReaderTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier.padding(vertical = 16.dp),
    ) {
        // Select font size
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painter = painterResource(R.drawable.format_size),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Slider(
                value = fontSizeValue,
                onValueChange = onFontSizeChange,
                valueRange = 8f..24f,
                colors = SliderDefaults.colors().copy(
                    thumbColor = Color(0xFF66558E),
                    activeTrackColor = Color(0xFF66558E),
                    // Loved this
                    // thumbColor = MaterialTheme.colorScheme.onBackground,
                    // activeTrackColor = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f)
            )
            Icon(
                painter = painterResource(R.drawable.format_size),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }

        // Select font family
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            FontButton(
                readerFont = ReaderFont.SourceSerif4,
                isSelected = fontSelected == ReaderFont.SourceSerif4,
                onClick = { onFontSelected(ReaderFont.SourceSerif4) },
                modifier = Modifier.weight(1f)
            )
            FontButton(
                readerFont = ReaderFont.Lora,
                isSelected = fontSelected == ReaderFont.Lora,
                onClick = { onFontSelected(ReaderFont.Lora) },
                modifier = Modifier.weight(1f)
            )
            FontButton(
                readerFont = ReaderFont.NotoSans,
                isSelected = fontSelected == ReaderFont.NotoSans,
                onClick = { onFontSelected(ReaderFont.NotoSans) },
                modifier = Modifier.weight(1f)
            )
        }

        // Select theme
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            ThemeButton(
                theme = ReaderTheme.Light,
                onClick = { onThemeSelected(ReaderTheme.Light) },
                modifier = Modifier.weight(1f)
            )
            ThemeButton(
                theme = ReaderTheme.Dark,
                onClick = { onThemeSelected(ReaderTheme.Dark) },
                modifier = Modifier.weight(1f)
            )
            ThemeButton(
                theme = ReaderTheme.Serpia,
                onClick = { onThemeSelected(ReaderTheme.Serpia) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
private fun ReaderModalSettingsPreview() {
    LightNovelTheme {
        ReaderModalSettings(
            fontSizeValue = 16f,
            onFontSizeChange = {},
            fontSelected = ReaderFont.SourceSerif4,
            onFontSelected = {},
            onThemeSelected = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        )
    }
}