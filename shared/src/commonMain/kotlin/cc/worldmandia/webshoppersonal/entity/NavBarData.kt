package cc.worldmandia.webshoppersonal.entity

import kotlinx.serialization.Serializable

@Serializable
data class NavBarData(
    val homePageTitle: String,
    val aboutUsTitle: String,
)
