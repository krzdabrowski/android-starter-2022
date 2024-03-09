@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.baseline.profile)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ktlint)
}

android {
    compileSdk = 34
    namespace = "eu.krzdabrowski.starter.baselineprofiles"

    with (defaultConfig) {
        minSdk = 28
        targetSdk = 34
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlin {
        jvmToolchain(17)
    }

    targetProjectPath = ":app"
}

androidComponents {
    onVariants { v ->
        v.instrumentationRunnerArguments.put(
            "targetAppId",
            v.testedApks.map { v.artifacts.getBuiltArtifactsLoader().load(it)?.applicationId }
        )
    }
}

baselineProfile {
    useConnectedDevices = true
}

dependencies {
    implementation(libs.test.android.benchmark.macro)
    implementation(libs.test.android.junit)

    detektPlugins(libs.detekt.compose.rules)
}
