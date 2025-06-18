package gb.coding.lightnovel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import gb.coding.lightnovel.core.navigation.Route
import gb.coding.lightnovel.core.navigation.bottomNavItems
import gb.coding.lightnovel.core.navigation.components.BottomNavigationBar
import gb.coding.lightnovel.reader.data.mock.MockChapters
import gb.coding.lightnovel.reader.presentation.browse.BrowseEvent
import gb.coding.lightnovel.reader.presentation.browse.BrowseScreen
import gb.coding.lightnovel.reader.presentation.browse.BrowseViewModel
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderEvent
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderScreen
import gb.coding.lightnovel.reader.presentation.chapter_reader.ChapterReaderViewModel
import gb.coding.lightnovel.reader.presentation.library.LibraryScreen
import gb.coding.lightnovel.reader.presentation.library.LibraryState
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailEvent
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailScreen
import gb.coding.lightnovel.reader.presentation.novel_detail.NovelDetailViewModel
import gb.coding.lightnovel.ui.theme.LightNovelTheme
import org.koin.androidx.compose.koinViewModel

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

                val browseViewModel = koinViewModel<BrowseViewModel>()
                val browseState by browseViewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) {
                    browseViewModel.events.collect { event ->
                        when (event) {
                            is BrowseEvent.Navigate2NovelDetail -> {
                                navController.navigate(Route.NovelDetail(event.novelId))
                            }
                        }
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
                                state = LibraryState(),
                                onAction = {
                                    navController.navigate(Route.NovelDetail)
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            )
                        }

                        composable<Route.Browse> {
                            BrowseScreen(
                                state = browseState,
                                onAction = browseViewModel::onAction,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }

                        composable<Route.NovelDetail> {
                            println("NovelDetailScreen | Composable")
                            val viewModel = koinViewModel<NovelDetailViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            NovelDetailScreen(
                                state = state,
                                onAction = viewModel::onAction,
                                modifier = Modifier.padding(innerPadding),
                            )

                            LaunchedEffect(Unit) {
                                viewModel.events.collect { event ->
                                    println("NovelDetailScreen | LaunchedEffect | event: $event")
                                    when (event) {
                                        is NovelDetailEvent.Navigate2ChapterReader -> {
                                            navController.navigate(Route.ChapterReader(event.chapterId))
                                        }
                                    }
                                }
                            }
                        }

                        composable<Route.ChapterReader> {
                            val viewModel = koinViewModel<ChapterReaderViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(Unit) {
                                viewModel.events.collect { event ->
                                    when (event) {
                                        ChapterReaderEvent.NavigateBack -> {
                                            navController.popBackStack()
                                        }
                                    }
                                }
                            }

                            ChapterReaderScreen(
                                state = state,
                                onAction = viewModel::onAction,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                    }
                }
            }
        }
    }
}