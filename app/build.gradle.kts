import com.android.build.gradle.internal.dsl.TestOptions

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {

    compileSdkVersion(BuildConfigs.compileSdk)

    defaultConfig {
        applicationId = "app.soulcramer.katafdj"
        minSdkVersion(BuildConfigs.minSdk)
        targetSdkVersion(BuildConfigs.targetSdk)
        versionCode = 1
        versionName = "0.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resConfigs("en", "fr")
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("debug") {
            isShrinkResources = false
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isTestCoverageEnabled = true
        }
    }

    testOptions {
        animationsDisabled = true

        unitTests(delegateClosureOf<TestOptions.UnitTestOptions> {
            isIncludeAndroidResources = true
        })
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Kotlin JDK
    implementation(Libraries.kotlinStandardLibrary)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)

    implementation(project(Modules.domain))
    implementation(project(Modules.presentation))
    implementation(project(Modules.remote))

    // AndroidX
    implementation(Libraries.AndroidX.activity)
    implementation(Libraries.AndroidX.constraintLayout)
    implementation(Libraries.AndroidX.swipeRefreshLayout)
    implementation(Libraries.AndroidX.core)
    implementation(Libraries.AndroidX.fragment)
    implementation(Libraries.AndroidX.materialComponent)
    implementation(Libraries.AndroidX.navigationFragment)
    implementation(Libraries.AndroidX.navigationUi)
    implementation(Libraries.AndroidX.recyclerView)

    // DI
    implementation(Libraries.koinAndroidXScope)

    // Leak Canary
    debugImplementation(Libraries.leackCanary)

    implementation(Libraries.glide)
    implementation(Libraries.glideAnnotation)
    kapt(Libraries.glideCompiler)

    implementation(Libraries.glideOkhttp)
    implementation(Libraries.timberKt)

    // Test
    testImplementation(Libraries.Test.core)
    testImplementation(Libraries.Test.runner)
    testImplementation(Libraries.Test.truth)
    testImplementation(Libraries.Test.truthKtx)
    testImplementation(Libraries.Test.robolectric)
    testImplementation(Libraries.Test.mockk)

    androidTestImplementation(Libraries.Test.core)
    debugImplementation(Libraries.Test.core)
    androidTestImplementation(Libraries.Test.runner)
    androidTestImplementation(Libraries.Test.truth)
    androidTestImplementation(Libraries.Test.truthKtx)
    androidTestImplementation(Libraries.Test.junitKtx)
    androidTestImplementation(Libraries.Test.koin)
    androidTestImplementation(Libraries.Test.mockkInstrumented)
    androidTestImplementation(Libraries.Test.espressoCore)
    androidTestImplementation(Libraries.Test.robolectricAnnotations)
    debugImplementation(Libraries.Test.fragment)
    androidTestImplementation(Libraries.Test.fragment)
}
