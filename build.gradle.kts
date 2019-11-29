import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-alpha03")
        classpath(kotlin("gradle-plugin", Versions.kotlin))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.AndroidX.navigation}")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

subprojects {
    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            // Set JVM target to 1.8
            jvmTarget = "1.8"
            freeCompilerArgs += "-Xuse-experimental=kotlinx.coroutine.ExperimentalCoroutineApi"
        }
    }
}

tasks {
    register<Delete>("clean") {
        description = "Delete the root project build folder"
        group = "cleanup"
        delete(rootProject.buildDir)
    }
}
