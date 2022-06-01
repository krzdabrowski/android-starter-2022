plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.ktLint)
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "eu.krzdabrowski.starter"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":basic-feature"))

    implementation(libs.composeActivity)
    implementation(libs.composeMaterial)
    implementation(libs.composeNavigation)
    implementation(libs.hilt)
    implementation(libs.kotlinCoroutines)
    implementation(libs.lifecycleRuntime)
    implementation(libs.timber)

    kapt(libs.hiltCompiler)
}
