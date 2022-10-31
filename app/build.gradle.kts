plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.junit)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

android {
    compileSdk = 33
    namespace = "eu.krzdabrowski.starter"

    defaultConfig {
        applicationId = "eu.krzdabrowski.starter"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "eu.krzdabrowski.starter.HiltTestRunner"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":basic-feature"))

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.common)
    implementation(libs.room.ktx)
    testImplementation(libs.bundles.common.test)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.android.compose)
    androidTestImplementation(libs.test.android.core)
    androidTestImplementation(libs.test.android.hilt)
    androidTestImplementation(libs.test.android.runner)

    kapt(libs.hilt.compiler)
    ksp(libs.room.compiler)
    kaptAndroidTest(libs.test.android.hilt.compiler)

    coreLibraryDesugaring(libs.desugar)

    detektPlugins(libs.detekt.twitter.compose)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
