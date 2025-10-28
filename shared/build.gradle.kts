import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
}

kotlin {
    jvm()

    js(IR) {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines)
            implementation(libs.koin)
            implementation(libs.serialization)
            implementation(libs.serialization.json)
            implementation(cryptographyLibs.core)
            implementation(cryptographyLibs.provider.optimal)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        val webMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(kotlinWrappers.browser)
                implementation(kotlinWrappers.cssomCore)
                implementation(kotlinWrappers.web)
            }
        }

        jsMain.get().dependsOn(webMain)
        wasmJsMain.get().dependsOn(webMain)
    }
}

