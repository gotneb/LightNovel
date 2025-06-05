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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import gb.coding.lightnovel.core.navigation.Route
import gb.coding.lightnovel.core.navigation.bottomNavItems
import gb.coding.lightnovel.core.navigation.components.BottomNavigationBar
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.reader.data.mock.MockNovels
import gb.coding.lightnovel.reader.presentation.browse.BrowseScreen
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderScreen
import gb.coding.lightnovel.reader.presentation.library.LibraryScreen
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailScreen
import gb.coding.lightnovel.ui.theme.LightNovelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LightNovelTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route

                var selectedNavigationIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                // Update the selected navigation item based on the current destination
                // Reason: when navigating back, we update the index of the selected tab
                LaunchedEffect(currentDestination) {
                    println("currentDestination: $currentDestination")

                    val currentIndex = bottomNavItems.indexOfFirst {
                        println("it.route::class.qualifiedName == ${it.route::class.qualifiedName}")
                        it.route::class.qualifiedName == currentDestination
                    }
                    if (currentIndex >= 0) {
                        selectedNavigationIndex = currentIndex
                    }
                }

                Scaffold(
                    bottomBar = {
                        // Only show bottom bar at "Library" or "Browse" screens
                        if (currentDestination == Route.Library::class.qualifiedName ||
                            currentDestination == Route.Browse::class.qualifiedName
                        ) {
                            BottomNavigationBar(
                                items = bottomNavItems,
                                selectedNavigationIndex = selectedNavigationIndex,
                                onItemSelected = { index ->
                                    selectedNavigationIndex = index
                                    val route = bottomNavItems[index].route
                                    navController.navigate(route) {
                                        popUpTo(route) {
                                            inclusive = true
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.Library,
                    ) {
                        composable<Route.Library> {
                            LibraryScreen(
                                novels = emptyList(),
                                onAction = {
                                    navController.navigate(Route.NovelDetail)
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable<Route.Browse> {
                            BrowseScreen(Modifier.padding(innerPadding))
                        }
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