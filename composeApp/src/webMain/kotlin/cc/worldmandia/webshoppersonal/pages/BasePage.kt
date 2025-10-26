package cc.worldmandia.webshoppersonal.pages

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import cc.worldmandia.webshoppersonal.LocalAppSettings
import cc.worldmandia.webshoppersonal.NavKeys
import cc.worldmandia.webshoppersonal.entity.BrowserData
import cc.worldmandia.webshoppersonal.models.BasePageViewModel
import cc.worldmandia.webshoppersonal.navSerializerModule
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import webshoppersonal.composeapp.generated.resources.Res
import webshoppersonal.composeapp.generated.resources.france
import webshoppersonal.composeapp.generated.resources.russia
import webshoppersonal.composeapp.generated.resources.ukraine


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

            val backStack = rememberNavBackStack(SavedStateConfiguration {
                navSerializerModule
            }, LocalAppSettings.current.navKey)

            Scaffold(
                modifier = Modifier.fillMaxSize().safeContentPadding()
                    .nestedScroll(scrollTopBehavior.nestedScrollConnection)
                    .nestedScroll(scrollBottomBehavior.nestedScrollConnection, nestedDispatcher), topBar = {
                    TopAppBar(
                        title = {
                            Text("Shop Name")
                        },
                        scrollBehavior = scrollTopBehavior,
                        actions = {
                            OutlinedButton(onClick = {
                                backStack.add(NavKeys.Contact)
                                basePageViewModel.updateNavKey(NavKeys.Contact)
                            }) {
                                Text("Contact us")
                            }
                            OutlinedButton(onClick = {
                                backStack.add(NavKeys.AboutUs)
                                basePageViewModel.updateNavKey(NavKeys.AboutUs)
                            }) {
                                Text("About us")
                            }
                            IconButton(onClick = {
                                scope.launch {
                                    lazyState.animateScrollToItem(lazyState.layoutInfo.totalItemsCount - 1)
                                }
                            }) {
                                Icon(Icons.Default.ArrowDownward, contentDescription = "Scroll to down")
                            }
                        }
                    )
                }, bottomBar = {
                    BottomAppBar(
                        scrollBehavior = scrollBottomBehavior,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var selectedLang by remember { mutableStateOf("Русский" to Res.drawable.russia) }

                            LanguageDropdown(
                                selectedLang, listOf(
                                    "Русский" to Res.drawable.russia,
                                    "Українська" to Res.drawable.ukraine,
                                    "Français" to Res.drawable.france,
                                ), modifier = Modifier.padding(8.dp)
                            ) {
                                selectedLang = it
                            }

                            Box(modifier = Modifier.padding(10.dp).size(48.dp)) {
                                IconButton(onClick = {
                                    scope.launch {
                                        lazyState.animateScrollToItem(0)
                                    }
                                }) {
                                    Icon(
                                        Icons.Default.ArrowUpward,
                                        contentDescription = "Scroll to top",
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
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
                NavDisplay(
                    backStack = backStack,
                    onBack = {
                        if (backStack.isNotEmpty()) backStack.removeLastOrNull()
                    },
                    entryProvider = entryProvider {
                        entry<NavKeys.MainPage> {
                            MainPageContent(lazyState = lazyState, innerPadding = innerPadding) {
                                Button(onClick = {
                                    // TODO
                                }) {
                                    Text("Try to back, but it's not possible")
                                }
                            }
                        }
                        entry<NavKeys.AboutUs> {
                            BaseContentPage(
                                lazyState = lazyState, innerPadding = innerPadding,
                                other = buildList {
                                    add {
                                        Button(onClick = {
                                            backStack[backStack.lastIndex] = NavKeys.MainPage
                                            basePageViewModel.updateNavKey(NavKeys.MainPage)
                                        }) {
                                            Text("Back")
                                        }
                                    }
                                },
                            )
                        }
                        entry<NavKeys.Contact> {
                            BaseContentPage(
                                lazyState = lazyState, innerPadding = innerPadding,
                                other = buildList {
                                    add {
                                        Button(onClick = {
                                            backStack[backStack.lastIndex] = NavKeys.MainPage
                                            basePageViewModel.updateNavKey(NavKeys.MainPage)
                                        }) {
                                            Text("Main page")
                                        }
                                    }
                                },
                            )
                        }
                    },
                    //transitionSpec = {
                    //    // Slide in from right when navigating forward
                    //    slideInHorizontally(initialOffsetX = { it }) togetherWith
                    //            slideOutHorizontally(targetOffsetX = { -it })
                    //},
                    //popTransitionSpec = {
                    //    // Slide in from left when navigating back
                    //    slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    //            slideOutHorizontally(targetOffsetX = { it })
                    //},
                    //predictivePopTransitionSpec = {
                    //    // Slide in from left when navigating back
                    //    slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    //            slideOutHorizontally(targetOffsetX = { it })
                    //},
                )
            }
        }
    }
}

@Composable
fun LanguageDropdown(
    currentLanguage: Pair<String, DrawableResource>,
    availableLanguages: List<Pair<String, DrawableResource>>,
    modifier: Modifier = Modifier,
    onLanguageSelected: (Pair<String, DrawableResource>) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = expanded, label = "dropdownTransition")

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) },
        label = "alpha"
    ) { if (it) 1f else 0f }

    val scale by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) },
        label = "scale"
    ) { if (it) 1f else 0.8f }

    Box(modifier = modifier) {
        IconButton(onClick = { expanded = !expanded }) {
            Image(
                painter = painterResource(currentLanguage.second),
                contentDescription = currentLanguage.first,
                modifier = Modifier.size(48.dp).clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.onSecondaryContainer, CircleShape)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = alpha
                    this.scaleX = scale
                    this.scaleY = scale
                }
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
        ) {
            availableLanguages
                .filter { it.first != currentLanguage.first }
                .forEach { language ->
                    DropdownMenuItem(
                        text = {
                            Text(language.first)
                        },
                        leadingIcon = {
                            Image(
                                painter = painterResource(language.second),
                                contentDescription = language.first,
                                modifier = Modifier.size(32.dp).clip(CircleShape)
                                    .border(1.dp, MaterialTheme.colorScheme.onSecondaryContainer, CircleShape)
                            )
                        },
                        onClick = {
                            onLanguageSelected(language)
                            expanded = false
                        }
                    )
                }
        }
    }
}