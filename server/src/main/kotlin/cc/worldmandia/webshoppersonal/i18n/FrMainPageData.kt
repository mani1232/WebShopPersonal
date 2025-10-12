package cc.worldmandia.webshoppersonal.i18n

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
