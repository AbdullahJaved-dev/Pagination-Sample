// Top-level build file where you can add configuration options common to all sub-projects/modules.

val navVersion by extra { "2.4.2" }
val gradleVersion by extra { "7.2.1" }
val lifecycleVersion by extra { "2.4.0" }
val appCenterSdkVersion by extra { "4.3.1" }

plugins {
    val gradleVersion = "7.2.1"
    id("com.android.application") version gradleVersion apply false
    id("com.android.library") version gradleVersion apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.4.2" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id("dagger.hilt.android.plugin") version "2.42" apply false
}

tasks.register("clean", Delete::class) {
    delete("${rootProject.buildDir}")
}
