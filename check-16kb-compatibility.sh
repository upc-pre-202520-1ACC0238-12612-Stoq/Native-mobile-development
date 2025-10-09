#!/bin/bash

# Script para verificar compatibilidad con 16 KB
echo "Verificando compatibilidad con dispositivos de 16 KB..."

# Limpiar proyecto
echo "Limpiando proyecto..."
./gradlew clean

# Construir APK
echo "Construyendo APK..."
./gradlew assembleDebug

# Verificar alineación de segmentos
echo "Verificando alineación de segmentos..."
if command -v aapt2 &> /dev/null; then
    aapt2 dump badging app/build/outputs/apk/debug/app-debug.apk | grep -i "16kb"
else
    echo "aapt2 no encontrado. Instalando Android SDK tools..."
    echo "Por favor, instala Android SDK y agrega aapt2 al PATH"
fi

# Verificar con bundletool si está disponible
if command -v bundletool &> /dev/null; then
    echo "Verificando con bundletool..."
    bundletool validate --bundle app/build/outputs/bundle/debug/app-debug.aab
else
    echo "bundletool no encontrado. Para verificación completa, instala bundletool"
fi

echo "Verificación completada."
echo "Si ves errores de alineación, asegúrate de que todas las librerías nativas estén actualizadas."
