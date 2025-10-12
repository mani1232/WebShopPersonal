package cc.worldmandia.webshoppersonal.i18n

import cc.worldmandia.webshoppersonal.entity.MainPageData
import cc.worldmandia.webshoppersonal.entity.SupportedLang

fun SupportedLang.mainPage(): MainPageData {
    return when (this) {
        SupportedLang.FR -> FrMainPageData
        else -> {
            error("Unsupported language: $this")
        }
    }
}