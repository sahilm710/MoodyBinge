import java.util.Properties  // ✅ Import added

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.moodybinge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moodybinge"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // ✅ Read TMDB API key from local.properties
        val props = Properties()
        val localFile = rootProject.file("local.properties")
        if (localFile.exists()) {
            props.load(localFile.inputStream())
        }

        val tmdbApiKey: String = props.getProperty("TMDB_API_KEY")
            ?: throw GradleException("TMDB_API_KEY is missing! Please add it in local.properties")

        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
    }

    buildFeatures {
        buildConfig = true
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
}

dependencies {
    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))

    // Firebase SDKs
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics")

    // AndroidX & Material
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    // Networking & JSON
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.11.0")

    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("androidx.preference:preference:1.2.1")

    // Glide (images)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

}
