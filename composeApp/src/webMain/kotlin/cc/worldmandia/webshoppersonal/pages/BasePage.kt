package cc.worldmandia.webshoppersonal.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation3.runtime.NavKey
import cc.worldmandia.webshoppersonal.LocalAppSettings
import cc.worldmandia.webshoppersonal.entity.BrowserData
import cc.worldmandia.webshoppersonal.models.BasePageViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.compose.getKoin

@Serializable
sealed class Routes {
    @Serializable
    object MainPage : NavKey

    @Serializable
    object AboutUs : NavKey

    @Serializable
    object Contact : NavKey
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BasePage(basePageViewModel: BasePageViewModel = getKoin().get()) {
    val settings by basePageViewModel.settings.collectAsState()

    CompositionLocalProvider(LocalAppSettings provides settings) {
        MaterialExpressiveTheme(colorScheme = settings.theme.scheme) {
            val scope = rememberCoroutineScope()

            val scrollTopBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

            val scrollBottomBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
            val nestedDispatcher = remember { NestedScrollDispatcher() }

            val lazyState = rememberLazyListState()

            Scaffold(
                modifier = Modifier.fillMaxSize().safeContentPadding()
                    .nestedScroll(scrollTopBehavior.nestedScrollConnection)
                    .nestedScroll(scrollBottomBehavior.nestedScrollConnection, nestedDispatcher), topBar = {
                    TopAppBar(
                        title = {
                            Text("Top app bar")
                        },
                        scrollBehavior = scrollTopBehavior
                    )
                }, bottomBar = {
                    BottomAppBar(
                        scrollBehavior = scrollBottomBehavior
                    ) {
                        Row(Modifier.align(Alignment.CenterVertically)) {
                            IconButton(onClick = {
                                scope.launch {
                                    lazyState.animateScrollToItem(0)
                                }
                            }) {
                                Icon(Icons.Default.ArrowUpward, contentDescription = "Scroll to top")
                            }
                        }
                    }
                }, floatingActionButton = {
                    FloatingActionButton(onClick = {
                        basePageViewModel.toggleTheme()
                    }) {
                        if (settings.theme == BrowserData.Theme.LIGHT) {
                            Icon(Icons.Default.DarkMode, contentDescription = "Dark")
                        } else {
                            Icon(Icons.Default.LightMode, contentDescription = "Light")
                        }
                    }
                }) { innerPadding ->

                MainPageContent(lazyState = lazyState, innerPadding = innerPadding)

                //val backStack = rememberNavBackStack(SavedStateConfiguration.DEFAULT, Routes.MainPage)
//
                //NavDisplay(
                //    backStack = backStack,
                //    onBack = { backStack.removeLastOrNull() },
                //    entryProvider = entryProvider {
                //        entry<Routes.MainPage> {
                //            MainPageContent(lazyState = lazyState, innerPadding = innerPadding) {
                //                Button(onClick = { backStack.add(Routes.AboutUs) }) {
                //                    Text("Go to Screen B")
                //                }
                //            }
                //        }
                //        entry<Routes.AboutUs> {
                //            Button(onClick = { backStack.add(Routes.Contact) }) {
                //                Text("Go to Screen C")
                //            }
                //        }
                //        entry<Routes.Contact>(
                //            metadata = NavDisplay.transitionSpec {
                //                // Slide new content up, keeping the old content in place underneath
                //                slideInVertically(
                //                    initialOffsetY = { it },
                //                    animationSpec = tween(1000)
                //                ) togetherWith ExitTransition.KeepUntilTransitionsFinished
                //            } + NavDisplay.popTransitionSpec {
                //                // Slide old content down, revealing the new content in place underneath
                //                EnterTransition.None togetherWith
                //                        slideOutVertically(
                //                            targetOffsetY = { it },
                //                            animationSpec = tween(1000)
                //                        )
                //            } + NavDisplay.predictivePopTransitionSpec {
                //                // Slide old content down, revealing the new content in place underneath
                //                EnterTransition.None togetherWith
                //                        slideOutVertically(
                //                            targetOffsetY = { it },
                //                            animationSpec = tween(1000)
                //                        )
                //            }
                //        ) {
                //            Text("This is Screen C")
                //        }
                //    },
                //    transitionSpec = {
                //        // Slide in from right when navigating forward
                //        slideInHorizontally(initialOffsetX = { it }) togetherWith
                //                slideOutHorizontally(targetOffsetX = { -it })
                //    },
                //    popTransitionSpec = {
                //        // Slide in from left when navigating back
                //        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                //                slideOutHorizontally(targetOffsetX = { it })
                //    },
                //    predictivePopTransitionSpec = {
                //        // Slide in from left when navigating back
                //        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                //                slideOutHorizontally(targetOffsetX = { it })
                //    },
                //    modifier = Modifier.padding(innerPadding)
                //)
            }
        }
    }
}