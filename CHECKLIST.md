# ✅ CHECKLIST ANTES DE COMPILAR

Usa esta lista para verificar que todo está configurado correctamente antes de compilar la aplicación.

## 📋 Pre-requisitos del Sistema

- [ ] **Java JDK 17** instalado
  - Verificar: Abrir terminal y ejecutar `java -version`
  - Debe mostrar versión 17.x

- [ ] **Android Studio** instalado (última versión recomendada)
  - Verificar: Abrir Android Studio
  - Help → About
  - Versión recomendada: 2023.1.1 o superior

- [ ] **Android SDK** instalado
  - Android Studio lo instala automáticamente
  - Verificar en: Tools → SDK Manager
  - SDK Platform: Android 11.0 (API 30) o superior

- [ ] **Conexión a Internet** activa
  - Necesaria para descargar dependencias de Gradle
  - Primera compilación puede descargar ~500MB

## 🗂️ Estructura de Archivos

- [ ] Todos los archivos del proyecto presentes en:
  ```
  C:\Users\jqnfu\Desktop\Proyectos\ClimaConvento\
  ```

- [ ] Estructura de carpetas correcta:
  - [ ] `app/src/main/java/com/climaconvento/`
  - [ ] `app/src/main/res/`
  - [ ] `app/src/main/AndroidManifest.xml`
  - [ ] `app/build.gradle`
  - [ ] `build.gradle`
  - [ ] `settings.gradle`

## 🔑 Configuración de MapBox (CRÍTICO)

- [ ] **Cuenta de MapBox creada**
  - Registrarse en: https://account.mapbox.com/auth/signup/

- [ ] **Access Token obtenido**
  - Copiar desde: https://account.mapbox.com/

- [ ] **Token configurado en strings.xml**
  - Archivo: `app/src/main/res/values/strings.xml`
  - Agregar línea:
    ```xml
    <string name="mapbox_access_token">pk.TU_TOKEN_AQUI</string>
    ```
  - ⚠️ **Sin esto, el mapa NO funcionará**

- [ ] **Token actualizado en build.gradle** (si usas esta opción)
  - Archivo: `app/build.gradle`
  - En `defaultConfig`, agregar:
    ```gradle
    manifestPlaceholders = [
        MAPBOX_ACCESS_TOKEN: "@string/mapbox_access_token"
    ]
    ```

## 🌐 Configuración de API de Clima

- [ ] **Credenciales de Weather Unlocked verificadas**
  - App ID: `021414f8` ✓ (ya incluido en el código)
  - App Key: `fc41f2ec43a577f1dc617965fb2e2d1e` ✓ (ya incluido)
  - Verificar en: `WeatherApiService.kt`

- [ ] **URLs de API correctas**
  - Base URL: `https://api.weatherunlocked.com/` ✓ (ya configurado)

## 📍 Coordenadas de Ubicaciones

- [ ] **Verificar coordenadas en Location.kt**
  - Archivo: `app/src/main/java/com/climaconvento/data/model/Location.kt`
  - Las coordenadas actuales son aproximadas
  - Ajustar si necesitas precisión exacta

- [ ] **Formato correcto de coordenadas**
  - Latitud: -90 a 90
  - Longitud: -180 a 180
  - Formato: `Location("Nombre", "Display", latitud, longitud)`

## 🎨 Personalización (Opcional)

- [ ] **Nombre de la aplicación**
  - Archivo: `strings.xml`
  - Cambiar `<string name="app_name">Clima Convento</string>`

- [ ] **Colores del tema**
  - Archivo: `colors.xml`
  - Ajustar `primary_blue`, etc.

- [ ] **Umbrales de temperatura**
  - Archivo: `WeatherMonitorWorker.kt`
  - `HIGH_TEMP_THRESHOLD` = 25.0°C
  - `LOW_TEMP_THRESHOLD` = 4.0°C

## 🔧 Configuración de Gradle

- [ ] **gradle.properties existe**
  - Ubicación: Raíz del proyecto
  - Contenido mínimo:
    ```properties
    org.gradle.jvmargs=-Xmx2048m
    android.useAndroidX=true
    android.enableJetifier=true
    ```

- [ ] **local.properties existe** (se crea automáticamente)
  - Ubicación: Raíz del proyecto
  - Contenido: Ruta al Android SDK
  - Si no existe, Android Studio lo creará

## 📱 Dispositivo de Prueba

Selecciona UNA de estas opciones:

### Opción A: Emulador Android

- [ ] **Emulador creado en Android Studio**
  - Tools → Device Manager → Create Device
  - Dispositivo recomendado: Pixel 5
  - System Image: Android 11 (API 30) o superior

- [ ] **Emulador iniciado**
  - Puede tardar 1-2 minutos en arrancar

### Opción B: Dispositivo Físico

- [ ] **Modo desarrollador activado**
  - Configuración → Acerca del teléfono
  - Tocar 7 veces en "Número de compilación"

- [ ] **Depuración USB habilitada**
  - Configuración → Opciones de desarrollador
  - Activar "Depuración USB"

- [ ] **Dispositivo conectado por USB**
  - Verificar que aparece en Android Studio
  - Aceptar diálogo de confianza en el dispositivo

- [ ] **Drivers USB instalados** (Windows)
  - Android Studio los instala automáticamente
  - Si no funciona, descargar de fabricante

## 🔍 Verificaciones Finales

- [ ] **Proyecto sincronizado con Gradle**
  - Android Studio → File → Sync Project with Gradle Files
  - Esperar a que termine (puede tardar varios minutos)

- [ ] **Sin errores de sincronización**
  - Ver panel "Build" en la parte inferior
  - Debe mostrar "BUILD SUCCESSFUL"

- [ ] **Configuración de ejecución correcta**
  - Barra superior: app → Dispositivo seleccionado

- [ ] **Conexión a Internet activa** (para primera compilación)
  - Gradle descargará dependencias
  - Aproximadamente 200-500 MB

## 🚀 Listo para Compilar

Si TODOS los items anteriores están marcados, proceder a compilar:

1. **Hacer clic en Run (▶️)** o presionar `Shift + F10`
2. **Esperar la compilación** (5-10 minutos la primera vez)
3. **La app se instalará automáticamente** en el dispositivo/emulador

## 🐛 Si hay Errores

### Error: "SDK location not found"
- [ ] Crear `local.properties` manualmente
- [ ] Agregar: `sdk.dir=C\:\\Users\\TU_USUARIO\\AppData\\Local\\Android\\Sdk`

### Error: "Failed to resolve: com.mapbox..."
- [ ] Verificar configuración de MapBox
- [ ] File → Sync Project with Gradle Files
- [ ] Verificar conexión a Internet

### Error: Gradle sync failed
- [ ] File → Invalidate Caches → Invalidate and Restart
- [ ] Eliminar carpeta `.gradle` en la raíz del proyecto
- [ ] Reiniciar Android Studio

### Error: Build failed
- [ ] Build → Clean Project
- [ ] Build → Rebuild Project
- [ ] Verificar que JDK 17 está configurado

## 📊 Estimaciones de Tiempo

| Tarea | Primera vez | Siguientes |
|-------|-------------|------------|
| Descargar dependencias | 5-10 min | - |
| Compilar proyecto | 5-10 min | 1-2 min |
| Instalar en dispositivo | 30-60 seg | 30-60 seg |
| **TOTAL** | **15-20 min** | **2-3 min** |

## ✅ Checklist Post-Compilación

Una vez que la app esté instalada:

- [ ] **La app abre sin crashes**
- [ ] **Se muestra el mapa**
- [ ] **Los marcadores aparecen en las ubicaciones**
- [ ] **Al tocar un marcador, aparece la información del clima**
- [ ] **El menú lateral se abre correctamente**
- [ ] **Las ubicaciones favoritas funcionan**
- [ ] **El botón "Ver Detalles" abre la pantalla de detalles**
- [ ] **Se muestra el pronóstico semanal**
- [ ] **Las notificaciones se crean (verificar después de 1 hora)**

## 📞 Recursos de Ayuda

Si necesitas ayuda adicional:

- [ ] **README.md** - Documentación completa
- [ ] **GUIA_COMPILACION.md** - Guía paso a paso
- [ ] **CONFIGURACION_MAPBOX.md** - Configuración de MapBox
- [ ] **NOTAS_TECNICAS.md** - Detalles técnicos
- [ ] **API_EJEMPLOS.md** - Ejemplos de API

## 🎯 Siguiente Paso

Si todos los checks están completos:

**→ Abrir Android Studio y ejecutar la app (▶️)**

---

**Fecha de verificación**: _______________  
**Verificado por**: _______________  
**Estado**: [ ] Listo para compilar [ ] Requiere ajustes
