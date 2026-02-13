# ========================================================================================
# PROGUARD RULES - TAMPLETE PROJECT
# ========================================================================================
# Este archivo contiene reglas de ofuscación y optimización para todas las librerías
# utilizadas en el proyecto. Mantener actualizado con nuevas dependencias.
# ========================================================================================

# ========================================================================================
# CONFIGURACIÓN GENERAL
# ========================================================================================
# Preservar información de línea para debugging de stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preservar atributos necesarios para reflexión y anotaciones
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Optimizaciones recomendadas
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# No advertir sobre clases referenciadas pero no usadas
-dontwarn javax.annotation.**
-dontwarn org.jetbrains.annotations.**
-dontwarn okhttp3.internal.platform.**
-dontwarn org.slf4j.**

# ========================================================================================
# KOTLIN & KOTLINX
# ========================================================================================
# Kotlin Reflection
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-dontwarn kotlin.reflect.**

# Kotlinx Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-keepclassmembers class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class org.override.tamplete.**$$serializer { *; }
-keepclassmembers class org.override.tamplete.** {
    *** Companion;
}
-keepclasseswithmembers class org.override.tamplete.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep class kotlinx.serialization.** { *; }

# Kotlinx DateTime
-keep class kotlinx.datetime.** { *; }
-dontwarn kotlinx.datetime.**

# ========================================================================================
# JETPACK COMPOSE
# ========================================================================================
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.foundation.** { *; }
-keep class androidx.compose.material3.** { *; }
-keep class androidx.compose.animation.** { *; }

# Mantener composables
-keep @androidx.compose.runtime.Composable class * { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# Adaptive Layout
-keep class androidx.compose.material3.adaptive.** { *; }

# ========================================================================================
# NAVIGATION 3
# ========================================================================================
-keep class androidx.navigation3.** { *; }
-keepclassmembers class androidx.navigation3.** { *; }
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# ========================================================================================
# ROOM DATABASE
# ========================================================================================
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *
-keepclassmembers class * extends androidx.room.RoomDatabase {
    public static ** getDatabase(android.content.Context);
}
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Mantener todas las clases de base de datos del proyecto
-keep class org.override.tamplete.core.data.local.** { *; }

# ========================================================================================
# KOIN (INYECCIÓN DE DEPENDENCIAS)
# ========================================================================================
-keep class org.koin.** { *; }
-keep class org.koin.core.** { *; }
-keep class org.koin.android.** { *; }
-keep class org.koin.androidx.** { *; }

# Mantener módulos de Koin
-keep class org.override.tamplete.di.** { *; }
-keepclassmembers class org.override.tamplete.di.** { *; }

# Mantener clases con anotaciones de Koin
-keep @org.koin.core.annotation.* class * { *; }
-keepclassmembers class * {
    @org.koin.core.annotation.* *;
}

# ========================================================================================
# KTOR CLIENT
# ========================================================================================
-keep class io.ktor.** { *; }
-keepclassmembers class io.ktor.** { *; }

# Ktor Serialization
-keep class io.ktor.serialization.** { *; }
-keep class io.ktor.client.plugins.contentnegotiation.** { *; }

# Ktor Engine
-keep class io.ktor.client.engine.okhttp.** { *; }

# Ktor Logging
-keep class io.ktor.client.plugins.logging.** { *; }

-dontwarn io.ktor.**
-dontwarn org.slf4j.**

# ========================================================================================
# OKHTTP & RETROFIT (Usado por Ktor)
# ========================================================================================
-keepattributes Signature
-keepattributes Exceptions
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# OkHttp Platform
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# ========================================================================================
# DATASTORE
# ========================================================================================
-keep class androidx.datastore.*.** { *; }

# Mantener serializers personalizados de DataStore
-keep class org.override.tamplete.feature.auth.data.local.UserSerializer { *; }
-keep class * implements androidx.datastore.core.Serializer {
    <init>(...);
}

# ========================================================================================
# FIREBASE
# ========================================================================================
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# Firebase Auth
-keep class com.google.firebase.auth.** { *; }

# Firebase Firestore
-keep class com.google.firebase.firestore.** { *; }
-keepclassmembers class com.google.firebase.firestore.** { *; }

# Firebase AI (Vertex AI)
-keep class com.google.firebase.vertexai.** { *; }

# Firestore Models - Mantener todas las clases de datos que se guardan en Firestore
-keepclassmembers class org.override.tamplete.**.model.** {
    <fields>;
    <init>();
}

# ========================================================================================
# GOOGLE PLAY SERVICES
# ========================================================================================
-keep class com.google.android.gms.auth.** { *; }
-keep class com.google.android.gms.common.** { *; }
-keep class com.google.android.gms.tasks.** { *; }

# Credentials API
-keep class androidx.credentials.** { *; }
-keepclassmembers class androidx.credentials.** { *; }

# ========================================================================================
# COIL (CARGA DE IMÁGENES)
# ========================================================================================
-keep class coil3.** { *; }
-keep interface coil3.** { *; }
-dontwarn coil3.**

# Coil Network
-keep class coil3.network.** { *; }

# ========================================================================================
# WORKMANAGER
# ========================================================================================
-keep class androidx.work.** { *; }
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.CoroutineWorker {
    public <init>(...);
}
-keepclassmembers class * extends androidx.work.Worker {
    public <init>(android.content.Context,androidx.work.WorkerParameters);
}

# ========================================================================================
# SPLASH SCREEN
# ========================================================================================
-keep class androidx.core.splashscreen.** { *; }

# ========================================================================================
# BIOMETRIC
# ========================================================================================
-keep class androidx.biometric.** { *; }

# ========================================================================================
# MATERIAL KOLOR
# ========================================================================================
-keep class com.materialkolor.** { *; }

# ========================================================================================
# ACCOMPANIST
# ========================================================================================
-keep class com.google.accompanist.** { *; }
-dontwarn com.google.accompanist.**

# ========================================================================================
# HAZE (BLUR EFFECTS)
# ========================================================================================
-keep class dev.chrisbanes.haze.** { *; }

# ========================================================================================
# FILEKIT
# ========================================================================================
-keep class io.github.vinceglb.filekit.** { *; }

# ========================================================================================
# QROSE (QR CODE)
# ========================================================================================
-keep class io.github.alexzhirkevich.qrose.** { *; }

# ========================================================================================
# RICHTEXT
# ========================================================================================
-keep class com.halilibo.richtext.** { *; }

# ========================================================================================
# MODELOS DE DATOS DEL PROYECTO
# ========================================================================================
# Mantener todos los modelos de dominio y DTOs
-keep class org.override.tamplete.feature.**.domain.model.** { *; }
-keep class org.override.tamplete.feature.**.data.**.dto.** { *; }
-keep class org.override.tamplete.feature.**.data.**.entity.** { *; }

# Mantener User y sus serializadores
-keep class org.override.tamplete.feature.auth.domain.model.User { *; }
-keepclassmembers class org.override.tamplete.feature.auth.domain.model.User { *; }

# ========================================================================================
# VIEWMODELS Y ESTADOS (MVI)
# ========================================================================================
# Mantener ViewModels
-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}

# Mantener States y Actions (MVI Pattern)
-keep class org.override.tamplete.**.presentation.**State { *; }
-keep interface org.override.tamplete.**.presentation.**Action { *; }
-keep class org.override.tamplete.**.presentation.**Action* { *; }
-keep class org.override.tamplete.main.Main** { *; }

# ========================================================================================
# REPOSITORIOS E INTERFACES
# ========================================================================================
-keep interface org.override.tamplete.**.domain.repository.** { *; }
-keep class org.override.tamplete.**.data.**Repository { *; }
-keep class org.override.tamplete.**.data.**RepositoryImpl { *; }

# ========================================================================================
# ENUMERACIONES Y SEALED CLASSES
# ========================================================================================
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Sealed classes y jerarquía de clases Kotlin
-keep class kotlin.** { *; }

# ========================================================================================
# PARCELABLE
# ========================================================================================
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# ========================================================================================
# TESTING (NO APLICA EN RELEASE, PERO BUENA PRÁCTICA)
# ========================================================================================
-dontwarn junit.**
-dontwarn org.mockito.**
-dontwarn org.junit.**

# ========================================================================================
# WARNINGS SUPRIMIDOS
# ========================================================================================
-dontwarn java.lang.management.**
-dontwarn javax.naming.**
-dontwarn sun.misc.**
-dontwarn android.test.**
-dontwarn com.android.tools.**

# ========================================================================================
# FIN DE PROGUARD RULES
# ========================================================================================
