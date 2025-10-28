import io.ktor.plugin.features.DockerImageRegistry
import io.ktor.plugin.features.DockerPortMapping
import io.ktor.plugin.features.DockerPortMappingProtocol

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
    jvmToolchain(25)
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

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_25)
        localImageName.set("wsh-backend")
        imageTag.set(version.toString())
        customBaseImage.set("azul/zulu-openjdk:25-latest")

        portMappings.set(listOf(
            DockerPortMapping(
                49152,
                49152,
                DockerPortMappingProtocol.TCP
            )
        ))

        externalRegistry.set(DockerImageRegistry.externalRegistry(
            username = providers.environmentVariable("GITHUB_USERNAME"),
            password = providers.environmentVariable("GITHUB_PASSWORD"),
            project = provider { "WebShopPersonal" },
            //hostname = provider { "ghcr.io" },
            //namespace = provider { "mani1232" }
        ))

        //externalRegistry.set(
        //    DockerImageRegistry.dockerHub(
        //        appName = provider { "wsh-backend" },
        //        username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
        //        password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
        //    )
        //)
        jib {
            container {
                workingDirectory = "/home/container"
            }
        }
    }
}