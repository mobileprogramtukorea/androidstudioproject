import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.final_project"
    compileSdk = 34
    viewBinding{
        enable = true
    }
    defaultConfig {
        applicationId = "com.example.final_project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        debug {
            resValue("string","WEATHER_API_KEY",gradleLocalProperties(rootDir,providers).getProperty("WEATHER_API_KEY"))
            buildConfigField("String","WEATHER_API_KEY", gradleLocalProperties(rootDir,providers).getProperty("WEATHER_API_KEY"))
            resValue("string","WEATHER_API_KEY2",gradleLocalProperties(rootDir,providers).getProperty("WEATHER_API_KEY2"))
            buildConfigField("String","WEATHER_API_KEY2", gradleLocalProperties(rootDir,providers).getProperty("WEATHER_API_KEY2"))

        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string","WEATHER_API_KEY",gradleLocalProperties(rootDir,providers).getProperty("WEATHER_API_KEY"))
            buildConfigField("String","WEATHER_API_KEY", gradleLocalProperties(rootDir,providers).getProperty("WEATHER_API_KEY"))
            resValue("string","WEATHER_API_KEY2",gradleLocalProperties(rootDir,providers).getProperty("WEATHER_API_KEY2"))
            buildConfigField("String","WEATHER_API_KEY2", gradleLocalProperties(rootDir,providers).getProperty("WEATHER_API_KEY2"))
        }


    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //날씨 API 통신 라이브러리
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
}
