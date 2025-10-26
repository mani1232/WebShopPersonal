package cc.worldmandia.webshoppersonal.db

import cc.worldmandia.webshoppersonal.entity.SupportedLang
import web.dom.ElementId
import web.dom.document
import web.navigator.navigator

fun getLanguage(): SupportedLang {
    return if (SupportedLang.entries.map { it.name }.contains(navigator.language)) {
        SupportedLang.valueOf(navigator.language.uppercase())
    } else {
        SupportedLang.FR
    }
}

fun testLang(): String {
    return navigator.language
}

fun removeLoader() = document.getElementById(ElementId("loader"))?.remove()