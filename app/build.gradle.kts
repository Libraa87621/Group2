plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.duan1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.duan1"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Android Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth:21.0.5") // Firebase Auth

    // Firebase BOM (Bill of Materials)
    implementation(platform("com.google.firebase:firebase-bom:31.0.2"))  // Correct use of platform

    // Firebase Authentication from BOM
    implementation("com.google.firebase:firebase-auth")

    // ZXing - QR Code scanning
    implementation("com.google.zxing:core:3.4.1") // QR Code core
    implementation("com.journeyapps:zxing-android-embedded:4.3.0") // Android wrapper for QR scanning

    // Test Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
