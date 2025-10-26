package cc.worldmandia.webshoppersonal.entity

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.navigation3.runtime.NavKey
import cc.worldmandia.webshoppersonal.NavKeys
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
data class BrowserData(
    val lang: SupportedLang = SupportedLang.FR,
    var theme: Theme = Theme.LIGHT,
    @Polymorphic
    var navKey: NavKey = NavKeys.MainPage,
) {
    @Serializable
    enum class Theme(val scheme: ColorScheme) { LIGHT(lightColorScheme()), DARK(darkColorScheme()) }
}