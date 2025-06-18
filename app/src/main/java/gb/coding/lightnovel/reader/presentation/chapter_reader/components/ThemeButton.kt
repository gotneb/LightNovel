package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.core.domain.model.ReaderTheme
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun ThemeButton(
    theme: ReaderTheme,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        border = if (theme == ReaderTheme.Light) BorderStroke(
            width = 2.dp,
            color = Color(0xFFE6E0E9),
        ) else null,
        colors = ButtonDefaults.buttonColors(
            containerColor = theme.backgroundColor,
            contentColor = theme.textColor,
        ),
        modifier = modifier,
    ) {
        Text(
            text = theme.label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 4.dp),
        )
    }
}

@Preview
@Composable
private fun SerpiaButtonPreview() {
    LightNovelTheme {
        ThemeButton(
            theme = ReaderTheme.Serpia,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun LightButtonPreview() {
    LightNovelTheme {
        ThemeButton(
            theme = ReaderTheme.Light,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun DarkButtonPreview() {
    LightNovelTheme {
        ThemeButton(
            theme = ReaderTheme.Dark,
            onClick = {},
        )
    }
}