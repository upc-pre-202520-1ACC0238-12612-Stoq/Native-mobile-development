# Reglas ProGuard específicas para compatibilidad con 16 KB
# Este archivo contiene reglas adicionales para asegurar la compatibilidad
# con dispositivos que usan page size de 16 KB

# Mantener alineación de bibliotecas nativas
-keep class **.R
-keep class **.R$*
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Excluir archivos problemáticos de dump_syms
-keep class !**/dump_syms/**
-dontwarn **/dump_syms/**

# Configuración para bibliotecas nativas
-keepclasseswithmembernames class * {
    native <methods>;
}

# Mantener clases de JNI
-keepclasseswithmembers class * {
    native <methods>;
}

# Configuración específica para 16 KB page size
-keep class com.google.firebase.crashlytics.** { *; }
-dontwarn com.google.firebase.crashlytics.**

# Excluir archivos binarios problemáticos
-keep class !**/*.bin
-keep class !**/dump_syms/**

# Configuración para Ktor (puede tener dependencias nativas)
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# Configuración para OkHttp (puede tener dependencias nativas)
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# Configuración para Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
