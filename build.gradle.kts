buildscript {
    dependencies {
        classpath("androidx.navigation.safeargs.kotlin:androidx.navigation.safeargs.kotlin.gradle.plugin:2.6.0")
    }
}
plugins {
    id("com.android.library") version ("8.0.2") apply false
    id("org.jetbrains.kotlin.android") version ("1.8.20") apply false
    id("com.android.application") version ("8.0.2") apply false
    id("com.google.dagger.hilt.android") version ("2.46.1") apply false
}