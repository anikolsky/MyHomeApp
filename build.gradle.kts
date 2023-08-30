buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.realm:realm-gradle-plugin:10.11.1")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.22" apply false
    id("io.realm.kotlin") version "1.10.2" apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
