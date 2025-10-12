package cc.worldmandia.webshoppersonal.pages

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cc.worldmandia.webshoppersonal.LocalAppSettings
import cc.worldmandia.webshoppersonal.models.MainPageViewModel
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import webshoppersonal.composeapp.generated.resources.Res
import webshoppersonal.composeapp.generated.resources.compose_multiplatform

@Composable
fun MainPageContent(
    lazyState: LazyListState,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier.fillMaxSize().padding(innerPadding),
    viewModel: MainPageViewModel = getKoin().get(),
    other: @Composable (() -> Unit) = {}
) {
    Box(modifier) {
        LazyColumn(
            modifier = Modifier.animateContentSize(),
            state = lazyState
        ) {
            repeat(5) {
                item {
                    Row(
                        modifier = Modifier
                            .shimmer() // <- Affects all subsequent UI elements
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp, 80.dp)
                                .background(Color.LightGray),
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(24.dp)
                                    .background(Color.LightGray),
                            )
                            Box(
                                modifier = Modifier
                                    .size(120.dp, 20.dp)
                                    .background(Color.LightGray),
                            )
                        }
                    }
                }
            }

            repeat(10) {
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

            item { other() }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(lazyState)
        )
    }
}

@Composable
fun EmbeddedCarousel(
    imageList: List<DrawableResource>,
    colorScheme: ColorScheme = LocalAppSettings.current.theme.scheme
) {
    val pagerState = rememberPagerState(imageList.size / 2, pageCount = { imageList.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Image(
                    painter = painterResource(imageList[page]),
                    contentDescription = "Image ${page + 1}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Column(Modifier.align(Alignment.CenterStart)) {
                AnimatedVisibility(
                    visible = pagerState.currentPage > 0,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally()
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
                            contentDescription = "Back",
                            tint = colorScheme.onSurface,
                            modifier = Modifier
                                .background(colorScheme.surface.copy(alpha = 0.6f), CircleShape)
                                .padding(8.dp)
                        )
                    }
                }
            }

            Column(Modifier.align(Alignment.CenterEnd)) {
                AnimatedVisibility(
                    visible = pagerState.currentPage < pagerState.pageCount - 1,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally(),
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
                            contentDescription = "Next",
                            tint = colorScheme.onSurface,
                            modifier = Modifier
                                .background(colorScheme.surface.copy(alpha = 0.6f), CircleShape)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        PageIndicators(pagerState = pagerState)
    }
}

@Composable
fun PageIndicators(pagerState: PagerState, colorScheme: ColorScheme = LocalAppSettings.current.theme.scheme) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val isSelected = pagerState.currentPage == iteration
            val indicatorColor = if (isSelected) {
                colorScheme.primary
            } else {
                colorScheme.outlineVariant
            }

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(indicatorColor)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(iteration)
                        }
                    }
            )
        }
    }
}