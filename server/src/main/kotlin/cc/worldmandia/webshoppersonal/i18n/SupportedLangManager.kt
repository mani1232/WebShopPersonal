package cc.worldmandia.webshoppersonal.i18n

import cc.worldmandia.webshoppersonal.entity.MainPageData
import cc.worldmandia.webshoppersonal.entity.SupportedLang
import cc.worldmandia.webshoppersonal.i18n.main.FrMainPageData
import cc.worldmandia.webshoppersonal.i18n.main.RuMainPageData
import cc.worldmandia.webshoppersonal.i18n.main.UaMainPageData

fun SupportedLang.mainPage(): MainPageData {
    return when (this) {
        SupportedLang.FR -> FrMainPageData
        SupportedLang.RU -> RuMainPageData
        SupportedLang.UA -> UaMainPageData
    }
}