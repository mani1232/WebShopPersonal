package cc.worldmandia.webshoppersonal

actual fun getPlatform(): Platform {
    return JvmPlatform()
}

class JvmPlatform : Platform {
    override val name: String = "Jvm for backend"
}