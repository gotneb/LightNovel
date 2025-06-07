import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties().apply {
    load(FileInputStream(File("local.properties")))
}

/*
 * FIX: The keys can be reverse engineered, but for now this is a good approach.
 */
val SUPABASE_URL = localProperties["SUPABASE_URL"] as String
val SUPABASE_ANON_KEY = localProperties["SUPABASE_ANON_KEY"] as String

android {
    namespace = "gb.coding.lightnovel"
    compileSdk = 35

    defaultConfig {
        applicationId = "gb.coding.lightnovel"
        // FIX: minSdk should be set to API 29.
        // For this MVP, we'll use 31, since it's too much workaround for now.
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "SUPABASE_URL", SUPABASE_URL)
            buildConfigField("String", "SUPABASE_ANON_KEY", SUPABASE_ANON_KEY)
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "SUPABASE_URL", SUPABASE_URL)
            buildConfigField("String", "SUPABASE_ANON_KEY", SUPABASE_ANON_KEY)
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
        buildConfig = true
        compose = true
    }
}

dependencies {

    with (libs) {
        implementation(platform(bom))
        implementation(bundles.supabase)

        implementation(bundles.compose)
        implementation(bundles.ktor)
        implementation(bundles.koin)
    }

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}