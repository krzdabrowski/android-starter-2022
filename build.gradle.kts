plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.junit) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
}

allprojects {
    apply(
        plugin = "io.gitlab.arturbosch.detekt"
    )

    detekt {
        buildUponDefaultConfig = true
        config = files("$rootDir/gradle/detekt.yml")
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}
