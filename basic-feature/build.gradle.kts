plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.detekt)
    alias(libs.plugins.junit)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

android {
    compileSdk = 34
    namespace = "eu.krzdabrowski.starter.basicfeature"

    with (defaultConfig) {
        minSdk = 26
        targetSdk = 34
    }

    defaultConfig {
        testInstrumentationRunner = "eu.krzdabrowski.starter.core.utils.HiltTestRunner"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            consumerProguardFiles("proguard-rules.pro")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    kotlin {
        jvmToolchain(17)
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }

    sourceSets {
        getByName("androidTest") {
            java.srcDir(project(":core").file("src/androidTest/java"))
        }
        getByName("test") {
            java.srcDir(project(":core").file("src/test/java"))
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation(platform(libs.compose.bom))
    implementation(libs.coil)
    implementation(libs.compose.material3)
    implementation(libs.hilt)
    implementation(libs.kotlin.coroutines)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.navigation)
    implementation(libs.navigation.hilt)
    implementation(libs.kotlin.serialization)
    implementation(libs.retrofit)
    implementation(libs.room)
    implementation(libs.timber)
    testImplementation(libs.bundles.common.test)
    androidTestImplementation(libs.bundles.common.android.test)
    debugImplementation(libs.debug.compose.manifest)

    ksp(libs.hilt.compiler)
    kspAndroidTest(libs.test.android.hilt.compiler)

    detektPlugins(libs.detekt.compose.rules)
}
