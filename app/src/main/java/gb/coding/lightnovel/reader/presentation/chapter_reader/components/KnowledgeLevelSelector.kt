package gb.coding.lightnovel.reader.presentation.chapter_reader.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun KnowledgeLevelSelector(
    selectedLevel: Int?,
    onLevelSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val labels = listOf("Ignore", "New", "Recognized", "Familiar", "Learned", "Known")

    // There must be a better way to do this -.-'
    val levelItems: List<@Composable (isSelected: Boolean) -> Unit> = listOf(
        { Icon(painter = painterResource(R.drawable.delete), contentDescription = "Ignore", tint = if (it) Color.White else MaterialTheme.colorScheme.onBackground) },
        { Text("1", fontSize = 16.sp, color = if (it) Color.White else MaterialTheme.colorScheme.onBackground) },
        { Text("2", fontSize = 16.sp, color = if (it) Color.White else MaterialTheme.colorScheme.onBackground) },
        { Text("3", fontSize = 16.sp, color = if (it) Color.White else MaterialTheme.colorScheme.onBackground) },
        { Text("4", fontSize = 16.sp, color = if (it) Color.White else MaterialTheme.colorScheme.onBackground) },
        { Icon(painter = painterResource(R.drawable.check), contentDescription = "Known", tint = if (it) Color.White else MaterialTheme.colorScheme.onBackground) }
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Knowledge Level",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                // .padding(horizontal = 12.dp)
        ) {
            val borderColor = if (isSystemInDarkTheme()) Color(0xFF4A4A4A) else Color(0xFFD9D9D9)

            levelItems.forEachIndexed { index, content ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val isSelected = selectedLevel == index
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .run{
                                if (!isSelected) this.border(width = 1.dp, color = borderColor, shape = CircleShape)
                                else this
                            }
                            .background(
                                if (isSelected) Color(0xFF66558E)
                                else MaterialTheme.colorScheme.background
                            )
                            .clickable { onLevelSelected(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        content(isSelected)
                    }

                    if (isSelected) {
                        Text(
                            text = labels[index],
                            fontSize = 12.sp,
                            color = Color(0xFF66558E),
                            modifier = Modifier.padding(top = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KnowledgeLevelSelectorPreview() {
    LightNovelTheme {
        KnowledgeLevelSelector(
            selectedLevel = 3,
            onLevelSelected = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}