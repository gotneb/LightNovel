package gb.coding.lightnovel.reader.presentation.novel_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.reader.data.mock.MockNovels
import gb.coding.lightnovel.reader.domain.models.Novel
import gb.coding.lightnovel.reader.presentation.novel_detail.components.ExpandableText
import gb.coding.lightnovel.reader.presentation.novel_detail.components.TagChip
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun NovelDetailScreen(
    state: NovelDetailState,
    onAction: (NovelDetailAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val infoShadow = Shadow(
        color = Color.Black.copy(alpha = 0.5f),
        offset = Offset(x = 2f, y = 2f),
        blurRadius = 4f
    )

    LazyColumn(modifier) {
        item {
            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.novel != null -> {
                    Column(Modifier.fillMaxWidth()) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            // Background image
                            Image(
                                painter = painterResource(id = R.drawable.image_placeholder),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .matchParentSize()
                                    .blur(8.dp)
                                    .padding(bottom = 16.dp)
                            )
                            // Gradient overlay
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Black.copy(alpha = 0.5f),
                                                Color.Transparent
                                            ),
                                        )
                                    )
                            )
                            // Content
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .matchParentSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.image_placeholder),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(8.dp))
                                        .shadow(
                                            elevation = 8.dp,
                                            shape = RoundedCornerShape(8.dp),
                                            clip = true,
                                        )
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier
                                        .padding(vertical = 16.dp)
                                        .fillMaxHeight()
                                ) {
                                    Text(
                                        text = state.novel.title,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        style = TextStyle(
                                            shadow = infoShadow,
                                        ),
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp),
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.person),
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier
                                                .padding(end = 4.dp)
                                                .size(20.dp)
                                        )
                                        Text(
                                            text = state.novel.author,
                                            fontSize = 16.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Medium,
                                            style = TextStyle(
                                                shadow = infoShadow,
                                            ),
                                        )
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically,) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.hourglass),
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier
                                                .padding(end = 4.dp)
                                                .size(20.dp)
                                        )
                                        Text(
                                            text = state.novel.status,
                                            fontSize = 16.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Medium,
                                            style = TextStyle(
                                                shadow = infoShadow,
                                            ),
                                        )
                                    }

                                }
                            }
                        }
                        Text(
                            text = "Sinopse",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        ExpandableText(
                            content = state.novel.description,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                        ) {
                            state.novel.tags.forEach { tag ->
                                TagChip(text = tag)
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "2137 capítulos",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                            )
                            IconButton(
                                onClick = { /* TODO */ },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.filter_list),
                                    tint = Color.Black,
                                    contentDescription = null,
                                )
                            }
                        }
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
        items(10) { index ->
            Column(
                Modifier
                    .clickable{ onAction(NovelDetailAction.OnChapterClicked(MockChapters.sample)) }
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Capítulo ${index + 1}: Saindo de Casa",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "25 de Dezembro, 2025",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )
                HorizontalDivider()
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun NovelDetailScreenPreview() {
    LightNovelTheme {
        NovelDetailScreen(
            state = NovelDetailState(),
            onAction = {},
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}