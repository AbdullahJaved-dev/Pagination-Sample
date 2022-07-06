import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val stagingURL: String = gradleLocalProperties(rootDir).getProperty("STAGING_URL")
val liveURL: String = gradleLocalProperties(rootDir).getProperty("LIVE_URL")
val apiUserName: String = gradleLocalProperties(rootDir).getProperty("API_USERNAME")
val apiPassword: String = gradleLocalProperties(rootDir).getProperty("API_PASSWORD")

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.sdsol.paginationsample"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", stagingURL)
        }

        getByName("release") {
            buildConfigField("String", "BASE_URL", liveURL)
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildTypes.forEach {
        it.buildConfigField("String", "API_USERNAME", apiUserName)
        it.buildConfigField("String", "API_PASSWORD", apiPassword)
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    val navVersion: String by rootProject.extra
    val lifecycleVersion: String by rootProject.extra

    // implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //OkHttp Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    //Gson Convertor
    implementation("com.google.code.gson:gson:2.8.9")

    //Activity & Fragment
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation("androidx.activity:activity-ktx:1.4.0")

    //Navigation Components
    /*implementation("androidx.navigation:navigation-fragment-ktx:${navVersion}")
    implementation("androidx.navigation:navigation-ui-ktx:${navVersion}")*/
    api("androidx.navigation:navigation-fragment-ktx:$navVersion")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    //Paging3
    implementation("androidx.paging:paging-runtime:3.2.0-alpha01")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.42")
    kapt("com.google.dagger:hilt-android-compiler:2.42")

    // Co-routines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

    //Sdp & ssp
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("com.intuit.ssp:ssp-android:1.0.6")

    //Coil
    implementation("io.coil-kt:coil:1.4.0")
    
    //Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")
}