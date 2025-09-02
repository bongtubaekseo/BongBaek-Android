import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

val properties = Properties().apply {
    val f = project.rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}

val kakaoNativeKey: String =
    providers.gradleProperty("KAKAO_KEY").orNull
        ?: System.getenv("KAKAO_KEY")
        ?: properties.getProperty("kakao.native.key")
        ?: throw GradleException("KAKAO_APP_KEY (or local kakao.app.key) is missing")

android {
    namespace = "com.bongtu.baekseo"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.bongtu.baekseo"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", properties.getProperty("base.url"))
        buildConfigField("String", "KAKAO_NATIVE_KEY", "\"$kakaoNativeKey\"")
        buildConfigField("String", "KAKAO_API_KEY", "\"${properties.getProperty("kakao.api.key")}\"")
        buildConfigField("String", "KAKAO_BASE_URL", properties.getProperty("kakao.base.url"))

        manifestPlaceholders["KAKAO_KEY"] = kakaoNativeKey
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = properties.getProperty("debug.key.alias")
            keyPassword = properties.getProperty("debug.key.password")
            storeFile = File("${project.rootDir.absolutePath}/keystore/bongbaek-debug-key.jks")
            storePassword = properties.getProperty("debug.store.password")
        }
        create("release") {
            keyAlias = properties.getProperty("release.key.alias")
            keyPassword = properties.getProperty("release.key.password")
            storeFile = File("${project.rootDir.absolutePath}/keystore/bongbaek-release-key.jks")
            storePassword = properties.getProperty("release.store.password")
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Test
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.test)

    // Debug
    debugImplementation(libs.bundles.debug)

    // Androidx
    implementation(libs.bundles.androidx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.kotlinx.immutable)
    implementation(libs.androidx.datastore.preferences)

    // Network
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.timber)
    implementation(libs.lottie)

    // Kakao
    implementation(libs.kakao.user)
    implementation(libs.kakao.map)

    // Process Phoenix
    implementation(libs.process.phoenix)
}
