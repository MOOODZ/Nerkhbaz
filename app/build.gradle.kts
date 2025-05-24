import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "ir.moodz.sarafkoochooloo"
    compileSdk = 35

    defaultConfig {
        applicationId = "ir.moodz.sarafkoochooloo"
        minSdk = 24
        targetSdk = 35
        versionCode = 12
        versionName = "1.0.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val keystorePropertiesFile = rootProject.file("local.properties")
    val keystoreProperties = Properties()

    keystoreProperties.load(keystorePropertiesFile.inputStream())

    buildTypes {
//        signingConfigs {
//            create("bazaar-release-config") {
//                storeFile = file(keystoreProperties["BAZAAR-KEYSTORE_FILE"] as String)
//                storePassword = keystoreProperties["BAZAAR-KEYSTORE_PASSWORD"] as String
//                keyAlias = keystoreProperties["BAZAAR-KEY_ALIAS"] as String
//                keyPassword = keystoreProperties["BAZAAR-KEY_PASSWORD"] as String
//            }
//        }
        debug {
            val baseUrl = gradleLocalProperties(rootDir , providers).getProperty("BASE_URL")
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        }
        release {
            val baseUrl = gradleLocalProperties(rootDir , providers).getProperty("BASE_URL")
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            signingConfig = signingConfigs.getByName("bazaar-release-config")
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

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {

    // KTX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    
    // Material 3
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.icons.extended)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // Koin
    implementation(libs.bundles.koin)

    // Ktor
    implementation(libs.bundles.ktor)

    // Timber
    implementation(libs.timber)

    // Glance
    implementation (libs.androidx.glance.appwidget)
    implementation (libs.androidx.glance.material3)

}