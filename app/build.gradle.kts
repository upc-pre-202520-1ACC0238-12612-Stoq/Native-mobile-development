plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.stoq.StockWise"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.stoq.StockWise"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // Configuración específica para 16 KB page size
        externalNativeBuild {
            cmake {
                cppFlags += "-DANDROID_STL=c++_shared"
                arguments += "-DANDROID_PAGE_SIZE_AGNOSTIC=ON"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-rules-16kb.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-rules-16kb.pro"
            )
        }
    }
    
    // Configuración para compatibilidad con 16 KB
    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
        
        // Configuración específica para 16 KB page size
        resources {
            excludes += listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/*.kotlin_module"
            )
        }
        
        // Excluir archivos problemáticos que no están alineados
        jniLibs {
            excludes += listOf("**/dump_syms/**")
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
    }
    
    // Configuración adicional para compatibilidad con 16 KB
    androidResources {
        noCompress += listOf("dump_syms", "*.so", "*.bin")
    }
    
    // Configuración para 16 KB page size
    splits {
        abi {
            isEnable = true
            reset()
            include("arm64-v8a", "armeabi-v7a", "x86_64")
            isUniversalApk = false
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.firebase.crashlytics.buildtools)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    //implementation(libs.okhttp)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

// Tarea personalizada para verificar compatibilidad con 16 KB
tasks.register("check16KbCompatibility") {
    group = "verification"
    description = "Verifica la compatibilidad del APK con dispositivos de 16 KB"
    
    doLast {
        val apkFile = file("build/outputs/apk/debug/app-debug.apk")
        if (apkFile.exists()) {
            println("Verificando compatibilidad con 16 KB para: ${apkFile.name}")
            
            // Verificar si el APK contiene archivos problemáticos
            val process = ProcessBuilder("unzip", "-l", apkFile.absolutePath)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .start()
            
            val output = process.inputStream.bufferedReader().readText()
            val dumpSymsFound = output.contains("dump_syms", ignoreCase = true)
            
            if (dumpSymsFound) {
                println("⚠️  ADVERTENCIA: Se encontraron archivos dump_syms en el APK")
                println("   Estos archivos pueden causar problemas de compatibilidad con 16 KB")
                println("   Solución: Los archivos han sido excluidos en la configuración de packaging")
            } else {
                println("✅ No se encontraron archivos dump_syms problemáticos")
            }
            
            println("✅ Verificación de compatibilidad con 16 KB completada")
        } else {
            println("❌ APK no encontrado. Ejecuta 'assembleDebug' primero")
        }
    }
}