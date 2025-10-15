package cc.worldmandia.webshoppersonal.pages

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BaseContentPage(
    lazyState: LazyListState,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier.fillMaxSize().padding(innerPadding),
    other: List<@Composable (() -> Unit)>
) {
    other.also { items ->
        Box(modifier) {
            LazyColumn(
                modifier = Modifier.animateContentSize(),
                state = lazyState
            ) {
                items(items.count()) { id ->
                    items[id].invoke()
                }
            }
        }
    }
}