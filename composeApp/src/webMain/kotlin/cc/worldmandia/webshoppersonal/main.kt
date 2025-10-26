package cc.worldmandia.webshoppersonal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation3.runtime.NavKey
import cc.worldmandia.webshoppersonal.db.removeLoader
import cc.worldmandia.webshoppersonal.di.coreModule
import cc.worldmandia.webshoppersonal.di.viewModule
import cc.worldmandia.webshoppersonal.entity.BrowserData
import cc.worldmandia.webshoppersonal.pages.BasePage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin

val LocalAppSettings = staticCompositionLocalOf<BrowserData> {
    error("No BrowserData provided")
}

val navSerializerModule = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(NavKeys.MainPage::class)
        subclass(NavKeys.AboutUs::class)
        subclass(NavKeys.Contact::class)
    }
}

@Serializable
sealed class NavKeys {
    @Serializable
    @SerialName("MainPage")
    object MainPage : NavKey, NavKeys()

    @Serializable
    @SerialName("AboutUs")
    object AboutUs : NavKey, NavKeys()

    @Serializable
    @SerialName("Contact")
    object Contact : NavKey, NavKeys()
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
fun main() = ComposeViewport("root") {
    KoinApplication(application = {
        startKoin {
            modules(listOf(coreModule, viewModule))
        }
    }) {
        BasePage()

        removeLoader()
    }
}