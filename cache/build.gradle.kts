import com.android.build.gradle.internal.dsl.TestOptions

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(BuildConfigs.compileSdk)

    defaultConfig {
        minSdkVersion(BuildConfigs.minSdk)
        targetSdkVersion(BuildConfigs.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf(
                    "room.incremental" to "true"
                )
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isTestCoverageEnabled = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        execution = "ANDROID_TEST_ORCHESTRATOR"
        animationsDisabled = true

        unitTests(delegateClosureOf<TestOptions.UnitTestOptions> {
            isIncludeAndroidResources = true
        })
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

kapt {
    correctErrorTypes = true
    mapDiagnosticLocations = true
}

dependencies {
    implementation(project(Modules.domain))

    // Kotlin
    implementation(Libraries.kotlinStandardLibrary)

    implementation(Libraries.AndroidX.room)
    implementation(Libraries.AndroidX.roomRuntime)
    kapt(Libraries.AndroidX.roomCompiler)

    implementation(Libraries.koin)
    implementation(Libraries.koinAndroid)

    testImplementation(Libraries.Test.core)
    testImplementation(Libraries.Test.kotlinCoroutines)
    testImplementation(Libraries.Test.runner)
    testImplementation(Libraries.Test.truth)
    testImplementation(Libraries.Test.truthKtx)
    testImplementation(Libraries.Test.robolectric)
    testImplementation(Libraries.Test.mockk)
    testImplementation(Libraries.Test.room)
}