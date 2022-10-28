plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.junit)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktLint)
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
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
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

    implementation(platform(libs.composeBom))
    implementation(libs.bundles.common)
    implementation(libs.material)
    implementation(libs.roomKtx)
    testImplementation(libs.bundles.commonTest)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.testAndroidCompose)
    androidTestImplementation(libs.testAndroidCore)
    androidTestImplementation(libs.testAndroidHilt)
    androidTestImplementation(libs.testAndroidRunner)

    kapt(libs.hiltCompiler)
    ksp(libs.roomCompiler)
    kaptAndroidTest(libs.testAndroidHiltCompiler)

    coreLibraryDesugaring(libs.desugar)

    detektPlugins(libs.detektTwitterCompose)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
