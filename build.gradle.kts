import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    extra["kotlin_version"] = "1.3.50"
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.android_gradle}")
        classpath(kotlin("gradle-plugin", Versions.kotlin))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.AndroidX.navigation}")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
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
