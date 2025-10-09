# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Reglas para compatibilidad con 16 KB
-keep class com.google.firebase.crashlytics.** { *; }
-keep class com.google.firebase.analytics.** { *; }

# Mantener clases de Ktor
-keep class io.ktor.** { *; }

# Mantener clases de serialización
-keep class kotlinx.serialization.** { *; }

# Mantener clases de Koin
-keep class org.koin.** { *; }

# Mantener clases de Retrofit
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Reglas específicas para 16 KB alignment
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod