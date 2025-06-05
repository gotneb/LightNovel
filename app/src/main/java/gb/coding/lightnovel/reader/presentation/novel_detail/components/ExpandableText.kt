package gb.coding.lightnovel.reader.presentation.novel_detail.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun ExpandableText(
    content: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 4,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .clickable { expanded = !expanded }
            .animateContentSize()
    ) {
        Text(
            text = content,
            fontSize = 16.sp,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = if (expanded) "Mostrar menos" else "Ler mais",
            color = Color(0xFF65558F),
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview
@Composable
private fun ExpandableTextPreview() {
    LightNovelTheme {
        ExpandableText(
            content = LoremIpsum(30).values.joinToString(" ")
        )
    }
}