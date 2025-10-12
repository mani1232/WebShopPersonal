package cc.worldmandia.webshoppersonal.entity

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import kotlinx.serialization.Serializable

@Serializable
data class BrowserData(
    val lang: SupportedLang = SupportedLang.FR,
    val theme: Theme = Theme.LIGHT,
) {
    @Serializable
    enum class Theme(val scheme: ColorScheme) { LIGHT(lightColorScheme()), DARK(darkColorScheme()) }
}