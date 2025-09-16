plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization")
}
android {
    namespace = "kz.atasuai.delivery"
    compileSdk = 35

    defaultConfig {
        applicationId = "kz.atasuai.delivery"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("androidx.health.connect:connect-client:1.1.0-alpha07")
    implementation ("androidx.work:work-runtime-ktx:2.9.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")
    implementation ("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("androidx.core:core-splashscreen:1.0.1")
    implementation ("com.kizitonwose.calendar:compose:2.6.0")
    implementation ("androidx.camera:camera-core:1.4.0")
    implementation ("androidx.camera:camera-camera2:1.4.0")
    implementation ("androidx.camera:camera-lifecycle:1.4.0")
    implementation ("androidx.camera:camera-view:1.4.0")
    implementation ("com.google.mlkit:barcode-scanning:17.3.0")
    implementation ("com.google.accompanist:accompanist-permissions:0.36.0")
    implementation("com.google.zxing:core:3.5.2")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("network.chaintech:qr-kit:2.0.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.github.yalantis:ucrop:2.2.11-native")


    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.runtime.livedata)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)

    // Network
    implementation(libs.okhttp)

    // JSON Parsing (keeping Gson, remove Moshi)
    implementation(libs.gson)

    // Image Loading
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    // Data Storage
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.preference.ktx)

    // Firebase
//    implementation(libs.firebase.vertexai)
//    implementation(libs.firebase.messaging.ktx)

    // Google Services
    implementation(libs.play.services.location)

    // UI Components & Utils
    implementation(libs.materialdatetimepicker)
    implementation(libs.android.image.cropper)
    implementation(libs.androidx.biometric)
    implementation("androidx.webkit:webkit:1.14.0")

    // Security
    implementation(libs.jwtdecode)

    // Accompanist (keeping essential ones)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.systemuicontroller.v0315beta)

    // Utils
    implementation(libs.guava)
    implementation(libs.play.services.auth.api.phone)
    implementation(libs.androidx.room.runtime.android)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}