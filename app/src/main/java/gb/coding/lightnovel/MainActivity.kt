package gb.coding.lightnovel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gb.coding.lightnovel.core.navigation.Route
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.reader.data.mock.MockNovels
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderScreen
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailScreen
import gb.coding.lightnovel.ui.theme.LightNovelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LightNovelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Route.NovelDetail,
                    ) {
                        composable<Route.NovelDetail> {
                            NovelDetailScreen(
                                novel = MockNovels.novel,
                                onAction = { chapter ->
                                    navController.navigate(Route.ChapterReader)
                                },
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                        composable<Route.ChapterReader> {
                            ChapterReaderScreen(
                                chapter = MockChapters.sample,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                    }
                }
            }
        }
    }
}