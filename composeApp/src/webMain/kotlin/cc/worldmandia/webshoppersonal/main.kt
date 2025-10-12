package cc.worldmandia.webshoppersonal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import cc.worldmandia.webshoppersonal.di.coreModule
import cc.worldmandia.webshoppersonal.di.viewModule
import cc.worldmandia.webshoppersonal.entity.BrowserData
import cc.worldmandia.webshoppersonal.pages.BasePage
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin

val LocalAppSettings = staticCompositionLocalOf<BrowserData> {
    error("No BrowserData provided")
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
fun main() = ComposeViewport {
    KoinApplication(application = {
        startKoin {
            modules(listOf(coreModule, viewModule))
        }
    }) {
        BasePage()
    }
}