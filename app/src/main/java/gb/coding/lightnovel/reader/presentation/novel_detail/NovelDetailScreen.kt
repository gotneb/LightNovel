package gb.coding.lightnovel.reader.presentation.novel_detail

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import gb.coding.lightnovel.R
import gb.coding.lightnovel.core.presentation.util.formatDate
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.reader.data.mock.MockNovels
import gb.coding.lightnovel.reader.presentation.novel_detail.components.ExpandableText
import gb.coding.lightnovel.reader.presentation.novel_detail.components.TagChip
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun NovelDetailScreen(
    state: NovelDetailState,
    onAction: (NovelDetailAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = Color(0xFF66558E))
        }
        return
    }
    
    val infoShadow = Shadow(
        color = Color.Black.copy(alpha = 0.5f),
        offset = Offset(x = 2f, y = 2f),
        blurRadius = 4f
    )

    // FIX: There must be a better way to handle this state
    // It's not so elegant using "!!" operator for each time novel is referenced
    LazyColumn(modifier) {
        item {
            Column(Modifier.fillMaxWidth()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    // Background image
                    AsyncImage(
                        model = if (LocalInspectionMode.current) R.drawable.image_placeholder else state.novel!!.coverImage,
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
                        AsyncImage(
                            model = if (LocalInspectionMode.current) R.drawable.image_placeholder else state.novel!!.coverImage,
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
                                text = state.novel!!.title,
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
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
                    content = state.novel!!.description,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    state.tags.forEach { tag ->
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
                        text = "${state.chapters.size} capítulos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    IconButton(
                        onClick = { onAction(NovelDetailAction.OnInvertChaptersListClicked) },
                    ) {
                        val rotation by animateFloatAsState(
                            targetValue = if (state.invertList) 180f else 0f,
                            label = "rotationAnimation"
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.filter_list),
                            contentDescription = null,
                            modifier = Modifier.rotate(rotation)
                        )
                    }
                }
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            }

        }
        items(
            items = state.chapters,
            key = { chapter -> chapter.id },
        ) { chapter ->
            Column(
                Modifier
                    .clickable{ onAction(NovelDetailAction.OnChapterClicked(chapter.id)) }
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Capítulo ${chapter.chapterNumber}: ${chapter.title}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = chapter.createdAt.formatDate(),
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
            state = NovelDetailState(
                novel = MockNovels.sample,
                chapters = MockChapters.samples,
                isLoading = false,
                tags = listOf("Action", "Adult", "Adventure", "Martial Arts", "Fantasy", "Romance", "Sobrenatural", "Xianxia"),
            ),
            onAction = {},
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}