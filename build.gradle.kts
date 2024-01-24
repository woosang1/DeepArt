// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven(url = "https://jitpack.io")
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.46")
//        classpath("com.google.gms:google-services:4.4.0")
    }
}