plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.realm.kotlin")
}

android {
    namespace = "com.dimrnhhh.moneytopia"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.dimrnhhh.moneytopia"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0-alpha.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        ndk {
            abiFilters.add("arm64-v8a")
            abiFilters.add("armeabi-v7a")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.0-beta01")

    // Realm Kotlin
    implementation("io.realm.kotlin:library-base:1.16.0")

    // SplashScreen
    implementation("androidx.core:core-splashscreen:1.1.0-rc01")

    // Kotlinx Coroutines Core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // Accompanist System UI Controller Library
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    // Compose Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended:1.7.0-beta02")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Material 3 Date & Time Picker
    implementation("com.marosseleng.android:compose-material3-datetime-pickers:0.7.2")

    // Lifecycle ViewModel Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")

    // AppCompat
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Vico Compose Charts
    implementation("com.patrykandpatrick.vico:compose:1.14.0")
    implementation("com.patrykandpatrick.vico:compose-m3:1.14.0")
    implementation("com.patrykandpatrick.vico:core:1.14.0")
}