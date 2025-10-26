plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
    application
}

group = "cc.worldmandia.webshoppersonal"
version = "1.0.0"
application {
    mainClass.set("cc.worldmandia.webshoppersonal.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

kotlin {
    jvmToolchain(24)
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.serialization)
    implementation(libs.ktor.serialization.json)
    testImplementation(libs.ktor.server.testHost)
    testImplementation(libs.kotlin.testJunit)

    implementation(libs.coroutines)
}