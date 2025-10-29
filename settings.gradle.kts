rootProject.name = "WebShopPersonal"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenCentral()
        gradlePluginPortal()
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
        mavenCentral()
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
    }
    versionCatalogs {
        val wrappersVersion = "2025.10.14"
        val cryptographyVersion = "0.5.0"
        create("kotlinWrappers") {
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
        create("cryptographyLibs") {
            from("dev.whyoleg.cryptography:cryptography-version-catalog:$cryptographyVersion")
        }
    }
}

include(":composeApp")
include(":server")
include(":shared")