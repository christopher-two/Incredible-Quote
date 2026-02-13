plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.ksp)
}

// Aplicar Google Services solo si existe el archivo google-services.json
val googleServicesFile = file("google-services.json")
if (googleServicesFile.exists()) {
    apply(plugin = libs.plugins.google.services.get().pluginId)
    println("✅ Google Services habilitado - google-services.json encontrado")
} else {
    println("⚠️  Google Services deshabilitado - google-services.json no encontrado")
    println("   Para habilitar Firebase, agrega el archivo google-services.json en app/")
}

val nameProject = "tamplete"

android {
    namespace = "org.override.$nameProject"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "org.override.$nameProject"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Habilitar minificación y ofuscación de código
            isMinifyEnabled = true
            // Reducir recursos no utilizados
            isShrinkResources = true
            // Archivos de configuración ProGuard
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // Desactivar en modo debug para facilitar el desarrollo
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // ==================== CORE ANDROID ====================
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.biometric)

    // ==================== COMPOSE UI ====================
    // BOM de Compose para gestión centralizada de versiones
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.adaptive)
    implementation(libs.adaptive.layout)
    implementation(libs.adaptive.navigation)
    implementation(libs.material.icons.ext)

    // ==================== NAVIGATION 3 ====================
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation3)

    // ==================== KOTLINX ====================
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.play)

    // ==================== KOIN (INYECCIÓN DE DEPENDENCIAS) ====================
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.koin.compose.navigation3)

    // ==================== ROOM (BASE DE DATOS LOCAL) ====================
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // ==================== COIL (CARGA DE IMÁGENES) ====================
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    // ==================== KTOR (CLIENTE HTTP) ====================
    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.logging)

    // ==================== FIREBASE (OPCIONAL) ====================
    // Solo se incluye si google-services.json existe
    if (googleServicesFile.exists()) {
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.auth)
        implementation(libs.firebase.firestore)
        implementation(libs.firebase.ai)
    }

    // ==================== GOOGLE SERVICES ====================
    implementation(libs.gms.auth)
    implementation(libs.credentials)
    implementation(libs.cred.play.services)

    // ==================== DATASTORE (PREFERENCIAS) ====================
    implementation(libs.datastore.pref)
    implementation(libs.datastore.pref.core)

    // ==================== WORKMANAGER ====================
    implementation(libs.work.runtime.ktx)

    // ==================== UI ADICIONAL ====================
    implementation(libs.material.kolor)
    implementation(libs.qrose)
    implementation(libs.richtext.ui)
    implementation(libs.richtext.ui.m3)
    implementation(libs.haze)
    implementation(libs.accompanist.permissions)

    // ==================== FILEKIT ====================
    implementation(libs.filekit.core)
    implementation(libs.filekit.dialogs)
    implementation(libs.filekit.dialogs.compose)
    implementation(libs.filekit.coil)

    // ==================== TESTING ====================
    testImplementation(libs.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // ==================== DEBUG/TOOLING ====================
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}