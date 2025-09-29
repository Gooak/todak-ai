plugins {
    
    // Android & Kotlin Plugins
    alias(libs.plugins.android.application)   // Android Application
    alias(libs.plugins.kotlin.android)        // Kotlin Android
    alias(libs.plugins.kotlin.compose)        // Kotlin Compose Plugin

    // Dependency Injection
    alias(libs.plugins.hilt)                  // Hilt (Dagger) Plugin

    // id("kotlin-kapt")                      // KAPT 플러그인 필수

    // KSP (Kotlin Symbol Processing)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.today_diary_ai"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.today_diary_ai"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    
    // Core & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    
    // Jetpack Compose UI
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    
    // Room (Database)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.ktx)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Hilt (Dependency Injection)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.hilt.work)
    implementation(libs.firebase.components)
    implementation(libs.androidx.compose.foundation.layout)
    ksp(libs.hilt.compiler)

    // Room Compiler (KSP)
    ksp(libs.room.compiler)

    // Testing
    // Unit tests
    testImplementation(libs.junit)

    // Android Instrumentation tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Debug tools
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Vico Compose
    implementation(libs.vico.compose)

    //permission
    implementation(libs.accompanist.permissions)
}