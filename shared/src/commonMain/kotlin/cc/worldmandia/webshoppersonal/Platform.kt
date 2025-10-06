package cc.worldmandia.webshoppersonal

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform