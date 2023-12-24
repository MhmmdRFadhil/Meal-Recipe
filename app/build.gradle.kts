plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.ryz.mealrecipe"
    compileSdk = (ConfigData.compileSdkVersion)

    defaultConfig {
        applicationId = "com.ryz.mealrecipe"
        minSdk = (ConfigData.minSdkVersion)
        targetSdk = (ConfigData.targetSdkVersion)
        versionCode = (ConfigData.versionCode)
        versionName = (ConfigData.versionName)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        flavorDimensions.add("api")

        productFlavors {
            create("dev") {
                this.dimension = "api"
                buildConfigField("String", "BASE_URL", "\"https://www.themealdb.com/\"")
            }
            create("prod") {
                this.dimension = "api"
                buildConfigField("String", "BASE_URL", "\"https://www.themealdb.com/\"")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(Dependencies.Deps.core)
    implementation(Dependencies.Deps.appCompat)
    implementation("com.google.android.material:material:1.11.0")
    implementation(Dependencies.Deps.constrainLayout)

    // VIEW MODEL
    implementation(Dependencies.Deps.lifecycleLiveData)
    implementation(Dependencies.Deps.lifecycleViewModel)

    // DAGGER HILT
    implementation(Dependencies.Deps.hiltAndroid)
    kapt(Dependencies.Deps.hiltCompiler)

    // NAVIGATION GRAPH
    implementation(Dependencies.Deps.navigationFragment)
    implementation(Dependencies.Deps.navigationUi)

    // RETROFIT
    implementation(Dependencies.Deps.okHttpLoggingInterceptor)
    implementation(Dependencies.Deps.retrofitConverterGson)
    implementation(Dependencies.Deps.retrofit)

    // ROOM
    implementation(Dependencies.Deps.roomRuntime)
    implementation(Dependencies.Deps.roomKtx)
    ksp(Dependencies.Deps.roomCompiler)

    // GLIDE
    implementation(Dependencies.Deps.glide)
    ksp(Dependencies.Deps.glideCompiler)

    // PALETTE
    implementation(Dependencies.Deps.palette)

    // FLEXBOX
    implementation(Dependencies.Deps.flexbox)

    // LOTTIE
    implementation(Dependencies.Deps.lottie)

    // TESTING
    testImplementation(Dependencies.Deps.jUnit)
    androidTestImplementation(Dependencies.Deps.jUnitTest)
    androidTestImplementation(Dependencies.Deps.espresso)
}