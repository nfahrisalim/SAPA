plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.sapa2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sapa2"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Platform untuk memastikan semua library Firebase kompatibel
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    // Dependensi Firebase (tanpa perlu menyebutkan versi)
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")
//    implementation("com.google.firebase:firebase-appcheck-playintegrity")
//    implementation("com.google.firebase:firebase-appcheck-debug") // Library debug Anda

    // Dependensi Google Play Services
    implementation("com.google.android.gms:play-services-location:21.3.0") // Gunakan versi terbaru yang stabil
    implementation("com.google.android.libraries.places:places:3.5.0")

    // Dependensi AndroidX dan lainnya (sesuaikan dengan alias 'libs' Anda jika perlu)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Dependensi pihak ketiga
    implementation(libs.circleimageview)
    implementation(libs.imagepicker)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.swiperefreshlayout)
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // Dependensi untuk testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}