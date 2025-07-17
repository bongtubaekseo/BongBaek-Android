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
    load(project.rootProject.file("local.properties").inputStream())
}
val kakaoApiKey = properties.getProperty("kakao.api.key")
val kakaoNativeKey = properties.getProperty("kakao.native.key")
val kakaoBaseUrl = properties.getProperty("kakao.base.url")

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

        buildConfigField("String", "DUMMY_URL", properties.getProperty("dummy.url"))
        buildConfigField("String", "BASE_URL", properties.getProperty("base.url"))
        buildConfigField("String", "USER_TOKEN", properties.getProperty("user.token"))

        manifestPlaceholders["KAKAO_KEY"] = kakaoNativeKey
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = File("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "android"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            buildConfigField("String", "KAKAO_API_KEY", "\"$kakaoApiKey\"")
            buildConfigField("String", "KAKAO_NATIVE_KEY", "\"$kakaoNativeKey\"")
            buildConfigField("String", "KAKAO_BASE_URL", "$kakaoBaseUrl")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "KAKAO_API_KEY", "\"$kakaoApiKey\"")
            buildConfigField("String", "KAKAO_NATIVE_KEY", "\"$kakaoNativeKey\"")
            buildConfigField("String", "KAKAO_BASE_URL", "$kakaoBaseUrl")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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
    implementation(libs.advanced.bottom.sheet)

    implementation(libs.accompanist.systemuicontroller)

    // Kakao
    implementation(libs.kakao.user)
    implementation(libs.kakao.map)
}
