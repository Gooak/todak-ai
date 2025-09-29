// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Android & Kotlin Plugins
    alias(libs.plugins.android.application) apply false   // Android Application
    alias(libs.plugins.kotlin.android) apply false        // Kotlin Android
    alias(libs.plugins.kotlin.compose) apply false        // Kotlin Compose Plugin

    // Dependency Injection
    alias(libs.plugins.hilt) apply false                  // Hilt (Dagger) Plugin

    // KSP (Kotlin Symbol Processing)
    alias(libs.plugins.ksp) apply false                   // KSP for annotation processors (Room/Hilt)

}
