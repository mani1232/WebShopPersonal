package cc.worldmandia.webshoppersonal.i18n.main

import cc.worldmandia.webshoppersonal.entity.MainPageData
import cc.worldmandia.webshoppersonal.entity.NavBarData
import kotlinx.serialization.Serializable

@Serializable
object FrMainPageData : MainPageData(
    navBarData = NavBarData(
        homePageTitle = "Accueil",
        aboutUsTitle = "À propos de nous",
    )
)

@Serializable
object RuMainPageData : MainPageData(
    navBarData = NavBarData(
        homePageTitle = "Главная",
        aboutUsTitle = "О нас",
    )
)

@Serializable
object UaMainPageData : MainPageData(
    navBarData = NavBarData(
        homePageTitle = "Головна",
        aboutUsTitle = "О нас",
    )
)