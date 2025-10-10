package cc.worldmandia.webshoppersonal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import cc.worldmandia.webshoppersonal.koin.startKoin
import cc.worldmandia.webshoppersonal.models.GlobalViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import webshoppersonal.composeapp.generated.resources.Res
import webshoppersonal.composeapp.generated.resources.compose_multiplatform


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
fun main() = ComposeViewport {
    KoinApplication(application = { startKoin() }) {
        val view: GlobalViewModel = getKoin().get()
        val scope = rememberCoroutineScope()
        var isDarkTheme by remember { mutableStateOf(false) }
        scope.launch {
            isDarkTheme = view.isDarkTheme()
        }
        MaterialExpressiveTheme(colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()) {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            val scrollBottomBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
            val lazyState = rememberLazyListState()

            Scaffold(
                modifier = Modifier.fillMaxSize().safeContentPadding()
                    .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
                    TopAppBar(
                        title = {
                            Text("Top app bar")
                        },
                        scrollBehavior = scrollBehavior
                    )
                }, bottomBar = {
                    BottomAppBar(
                        scrollBehavior = scrollBottomBehavior
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Bottom app bar",
                        )
                    }
                }, floatingActionButton = {
                    FloatingActionButton(onClick = {
                        scope.launch {
                            isDarkTheme = view.toggleTheme()
                        }
                    }) {
                        if (!isDarkTheme) {
                            Icon(Icons.Default.DarkMode, contentDescription = "Dark")
                        } else {
                            Icon(Icons.Default.LightMode, contentDescription = "Light")
                        }
                    }
                }) { innerPadding ->
                Box(Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier,
                        state = lazyState
                    ) {
                        item {
                            repeat(10) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button 0 times.
                """.trimIndent(),
                                )
                            }
                        }

                        item {
                            EmbeddedCarousel(
                                imageList = listOf(
                                    Res.drawable.compose_multiplatform,
                                    Res.drawable.compose_multiplatform,
                                    Res.drawable.compose_multiplatform,
                                    Res.drawable.compose_multiplatform,
                                    Res.drawable.compose_multiplatform,
                                )
                            )
                        }

                        item {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button 0 times.
                """.trimIndent(),
                            )
                        }
                    }
                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(lazyState)
                    )
                }
            }
        }
    }
}

@Composable
fun EmbeddedCarousel(imageList: List<DrawableResource>) {
    // Состояние для Pager
    val pagerState = rememberPagerState(pageCount = { imageList.size })
    val coroutineScope = rememberCoroutineScope() // Для прокрутки по нажатию кнопки

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Центрируем карусель по вертикали, если она не заполняет всю высоту
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp) // Фиксированная высота для карусели
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                // Каждая страница занимает всю доступную ширину и высоту Box'а
                Image(
                    painter = painterResource(imageList[page]),
                    contentDescription = "Фото ${page + 1}",
                    contentScale = ContentScale.Crop, // Обрезаем изображение, чтобы оно заполняло доступное пространство
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)) // Скругляем углы изображения
                )
            }

            // Кнопка "Назад"
            Column(Modifier.align(Alignment.CenterStart)) {
                AnimatedVisibility(
                    visible = pagerState.currentPage > 0, // Показываем кнопку только если это не первая страница
                ) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Предыдущее фото",
                            tint = Color.White,
                            modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape).padding(8.dp)
                        )
                    }
                }
            }


            // Кнопка "Вперед"
            Column(Modifier.align(Alignment.CenterEnd)) {
                AnimatedVisibility(
                    visible = pagerState.currentPage < pagerState.pageCount - 1, // Показываем кнопку только если это не последняя страница
                ) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Следующее фото",
                            tint = Color.White,
                            modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape).padding(8.dp)
                        )
                    }
                }
            }
        }

        // Индикаторы страниц
        Spacer(Modifier.height(16.dp)) // Отступ между каруселью и индикаторами
        PageIndicators(pagerState = pagerState)
    }
}

@Composable
fun PageIndicators(pagerState: PagerState) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(10.dp)
            ) {
                val scope = rememberCoroutineScope()
                OutlinedButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = iteration)
                    }
                }) {}
            }
        }
    }
}