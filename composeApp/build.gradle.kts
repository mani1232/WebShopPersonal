import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    applyDefaultHierarchyTemplate()
    js(IR) {
        browser()
        binaries.executable()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation("org.jetbrains.compose.material3:material3:1.10.0-alpha04+dev3171")
            implementation(compose.ui)
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
            implementation("org.jetbrains.compose.material3:material3-adaptive-navigation-suite:1.10.0-alpha04+dev3171")
            implementation(compose.components.resources)
            implementation(compose.preview)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.ui)
            implementation(libs.savedstate)

            implementation(libs.shimmer)

            implementation(projects.shared)

            implementation(libs.coroutines)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.json)

            implementation(libs.coil.compose)
            implementation(libs.coil.ktor3)
            // implementation(libs.coil.avif) not support wasm yet

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.navigation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}


