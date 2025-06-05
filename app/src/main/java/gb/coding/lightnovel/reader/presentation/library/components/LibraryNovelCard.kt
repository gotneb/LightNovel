package gb.coding.lightnovel.reader.presentation.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.reader.data.mock.MockNovels
import gb.coding.lightnovel.reader.domain.models.Novel
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun LibraryNovelCard(
    novel: Novel,
    onClick: (Novel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.clickable{
        // TODO: Implement with actual data
        //onAction(LibraryAction.OnNovelClicked(MockNovels.novel))
        onClick(novel)
    }) {
        Image(
            painter = painterResource(R.drawable.image_placeholder),
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = "Renegade Immortal",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun LibraryNovelCardPreview() {
    LightNovelTheme {
        LibraryNovelCard(
            novel = MockNovels.sample,
            onClick = {},
        )
    }
}