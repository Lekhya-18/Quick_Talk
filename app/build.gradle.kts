plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}
android {
    namespace = "com.example.smartchatapp"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.example.smartchatapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    // Firebase Auth
    implementation("com.google.firebase:firebase-auth:22.3.0")
}