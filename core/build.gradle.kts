plugins {

    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")

}

android {
    namespace = "com.codewithanil.otpkit.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }



    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    publishing {
        singleVariant("release")
    }

}

kotlin {
    jvmToolchain(11)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
            }
        }
    }
}

dependencies {



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Compose BOM
    implementation(platform(libs.androidx.compose.bom.v20240600))
    implementation(libs.androidx.compose.runtime)

    // Core UI
    implementation(libs.ui)
    implementation(libs.androidx.foundation)
    implementation(libs.material3)

    implementation(libs.androidx.ui.tooling.preview)

    debugImplementation(libs.ui.tooling)

}


