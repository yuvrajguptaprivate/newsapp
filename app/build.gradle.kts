plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.example.mynewsapiclient"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mynewsapiclient"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val MY_KEY = project.properties["MY_KEY"].toString()
        buildConfigField("String","API_Key",MY_KEY)
        val URL = project.properties["URL"].toString()
        buildConfigField("String","BASE_URL",URL)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    buildFeatures{
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.room.common)
    val lifecycle_version = "2.8.7"
    val nav_version = "2.8.6"
    val room_version = "2.6.1"

//nav
    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")
    //gson
    implementation ("com.google.code.gson:gson:2.12.1")
    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
//mockwebserver
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
//truth
    testImplementation ("com.google.truth:truth:1.4.4")
//okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
//hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
//glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2")

//room
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}