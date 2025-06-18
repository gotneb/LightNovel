package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.core.domain.mapper.toFontFamily
import gb.coding.lightnovel.core.domain.model.ReaderFont
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun FontButton(
    readerFont: ReaderFont,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(50),
            border = BorderStroke(
                width = 2.dp,
                color =  if (isSelected) Color(0xFF4D3D75) else Color(0xFFE6E0E9),
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                // containerColor = if (isSelected) Color(0xFFEDEAFF) else Color.Transparent,
                contentColor = Color(0xFF302E33),
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Aa",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontFamily = readerFont.toFontFamily(),
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(vertical = 4.dp),
            )
        }
        Text(
            text = readerFont.label,
            color = if (isSelected) Color(0xFF4D3D75) else MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Preview
@Composable
private fun NotSelectedPreview() {
    LightNovelTheme {
        FontButton(
            readerFont = ReaderFont.SourceSerif4,
            isSelected = false,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Preview
@Composable
private fun SelectedPreview() {
    LightNovelTheme {
        FontButton(
            readerFont = ReaderFont.SourceSerif4,
            isSelected = true,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}