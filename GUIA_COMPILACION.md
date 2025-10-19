# GUÍA RÁPIDA DE COMPILACIÓN Y EJECUCIÓN

## 📋 Pre-requisitos

Antes de comenzar, asegúrate de tener instalado:

1. **Java Development Kit (JDK) 17**
   - Descargar: https://www.oracle.com/java/technologies/downloads/#java17
   - O usar OpenJDK: https://adoptium.net/

2. **Android Studio** (última versión)
   - Descargar: https://developer.android.com/studio
   - Incluye Android SDK, emulador, y herramientas necesarias

3. **Conexión a Internet** (para descargar dependencias)

## 🚀 Pasos para Ejecutar la Aplicación

### PASO 1: Configurar MapBox (OBLIGATORIO)

⚠️ **IMPORTANTE**: Lee y sigue las instrucciones en `CONFIGURACION_MAPBOX.md` antes de continuar.

Resumen rápido:
1. Crear cuenta en MapBox (gratis)
2. Obtener Access Token
3. Agregarlo a `app/src/main/res/values/strings.xml`:

```xml
<string name="mapbox_access_token">pk.TU_TOKEN_AQUI</string>
```

### PASO 2: Abrir el Proyecto

1. Abrir **Android Studio**
2. Seleccionar: `File` → `Open`
3. Navegar a: `C:\Users\jqnfu\Desktop\Proyectos\ClimaConvento`
4. Hacer clic en `OK`

### PASO 3: Sincronizar Gradle

Android Studio automáticamente detectará los archivos de Gradle y te pedirá sincronizar:

1. Esperar el mensaje: "Gradle files have changed..."
2. Hacer clic en `Sync Now`
3. Esperar a que descargue todas las dependencias (puede tardar 5-10 minutos la primera vez)

Si hay errores:
- Verificar conexión a Internet
- `File` → `Invalidate Caches` → `Invalidate and Restart`

### PASO 4: Configurar Dispositivo

#### Opción A: Usar Emulador (Recomendado para pruebas)

1. En Android Studio: `Tools` → `Device Manager`
2. Hacer clic en `Create Device`
3. Seleccionar: **Pixel 5** o cualquier dispositivo moderno
4. System Image: **Android 11 (API 30)** o superior
5. Hacer clic en `Finish`
6. Iniciar el emulador haciendo clic en el ▶️ verde

#### Opción B: Usar Dispositivo Físico

1. En tu dispositivo Android:
   - `Configuración` → `Acerca del teléfono`
   - Tocar 7 veces en `Número de compilación` (activa modo desarrollador)
   - Volver a Configuración → `Opciones de desarrollador`
   - Activar `Depuración USB`

2. Conectar dispositivo a la PC con cable USB

3. En el dispositivo, aceptar: "¿Permitir depuración USB?"

4. En Android Studio, el dispositivo aparecerá en la lista de dispositivos

### PASO 5: Compilar y Ejecutar

1. En la barra superior de Android Studio, verificar:
   - Configuración: `app`
   - Dispositivo: Tu emulador o dispositivo físico

2. Hacer clic en el botón verde **Run** (▶️) o presionar `Shift + F10`

3. Esperar la compilación:
   - Primera vez: 5-10 minutos
   - Compilaciones posteriores: 1-2 minutos

4. La aplicación se instalará y abrirá automáticamente en el dispositivo

### PASO 6: Primera Ejecución

Al abrir la app por primera vez:

1. **Verificación de conectividad**:
   - Asegurarse de que el emulador/dispositivo tenga Internet
   - Si aparece error de red, verificar configuración

2. **Permisos de notificaciones** (Android 13+):
   - La app pedirá permiso para enviar notificaciones
   - Hacer clic en `Permitir`

3. **Pantalla principal**:
   - Verás el mapa centrado en Chile
   - 5 marcadores en las ubicaciones favoritas
   - Card inferior con clima de Convento (ubicación predeterminada)

## 🐛 Solución de Problemas Comunes

### Error: "SDK location not found"
**Solución**: Crear archivo `local.properties` en la raíz del proyecto:
```properties
sdk.dir=C\:\\Users\\TU_USUARIO\\AppData\\Local\\Android\\Sdk
```

### Error: "Failed to resolve: com.mapbox..."
**Causa**: Token de MapBox no configurado o dependencias no descargadas
**Solución**:
1. Verificar configuración de MapBox
2. `File` → `Sync Project with Gradle Files`
3. Verificar conexión a Internet

### Error: "Compilation failed"
**Solución**:
1. `Build` → `Clean Project`
2. `Build` → `Rebuild Project`
3. Verificar que JDK 17 esté instalado

### El mapa aparece en blanco
**Causa**: Token de MapBox inválido
**Solución**:
1. Verificar que el token esté correctamente copiado en `strings.xml`
2. Sin espacios extra ni caracteres especiales
3. Rebuild del proyecto

### No aparecen datos del clima
**Causa**: Sin conexión a Internet o API no responde
**Solución**:
1. Verificar conectividad del dispositivo/emulador
2. En emulador: Settings → Network & Internet → verificar estado
3. Revisar Logcat para errores específicos

### Notificaciones no funcionan en el emulador
**Causa**: Configuración del emulador
**Solución**:
1. En el emulador: Settings → Apps → Clima Convento → Notifications
2. Activar todas las notificaciones
3. Para Android 13+: Verificar que se otorgó el permiso

## 📊 Monitoreo y Debug

### Ver Logs en tiempo real:

1. En Android Studio: `View` → `Tool Windows` → `Logcat`
2. Filtrar por: `com.climaconvento`
3. Ver logs de API, alertas, y errores

### Inspeccionar Base de Datos:

1. `View` → `Tool Windows` → `App Inspection`
2. Seleccionar: `Database Inspector`
3. Ver tabla `weather_alerts`

### Verificar llamadas a la API:

En Logcat, buscar logs de Retrofit:
- `D/OkHttp: --> GET http://api.weatherunlocked.com/...`
- Ver respuestas y errores

## 🔄 Compilar APK para Distribución

Para instalar en otros dispositivos sin Android Studio:

1. En Android Studio: `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`

2. Esperar a que termine la compilación

3. Hacer clic en `locate` en la notificación

4. El APK estará en: `app/build/outputs/apk/debug/app-debug.apk`

5. Transferir el APK al dispositivo Android e instalar

**Nota**: Para producción, usar `Build` → `Generate Signed Bundle / APK`

## 📱 Probar Funcionalidades

### 1. Mapa de Temperatura
- Tocar marcadores en diferentes ubicaciones
- Verificar cambio de colores según temperatura
- Zoom in/out y navegación

### 2. Ubicaciones Favoritas
- Abrir menú lateral (☰)
- Seleccionar cada ubicación
- Verificar actualización de datos

### 3. Detalles de Ubicación
- Tocar "Ver Detalles"
- Verificar pronóstico semanal
- Comprobar viento en m/s

### 4. Sistema de Alertas
- Esperar a que el WorkManager ejecute (puede tardar hasta 1 hora)
- O simular alertas modificando los umbrales en `WeatherMonitorWorker.kt`
- Verificar notificación push
- Abrir sección de notificaciones

### 5. Verificación de Conectividad
- Desactivar WiFi y datos en el emulador/dispositivo
- Reiniciar la app
- Verificar diálogo de error
- Reactivar conexión y reintentar

## 🎯 Próximos Pasos

Una vez que la app funcione correctamente:

1. **Personalizar ubicaciones** (opcional):
   - Editar `Location.kt` para agregar/modificar coordenadas
   - Las coordenadas actuales son aproximadas

2. **Ajustar umbrales de temperatura**:
   - Editar `WeatherMonitorWorker.kt`
   - Cambiar `HIGH_TEMP_THRESHOLD` y `LOW_TEMP_THRESHOLD`

3. **Modificar frecuencia de actualización**:
   - En `MainActivity.kt`, método `setupWorkManager()`
   - Cambiar de `1, TimeUnit.HOURS` a tu preferencia

4. **Personalizar diseño**:
   - Colores: `res/values/colors.xml`
   - Textos: `res/values/strings.xml`
   - Estilos: `res/values/themes.xml`

## 📞 Ayuda Adicional

Si encuentras problemas:

1. **Revisar Logcat** para mensajes de error específicos
2. **Verificar configuraciones** en `CONFIGURACION_MAPBOX.md`
3. **Limpiar y reconstruir** el proyecto
4. **Actualizar Android Studio** a la última versión
5. **Revisar README.md** para documentación completa

---

**¡Éxito con tu aplicación de clima!** 🌤️

**Tiempo estimado total**: 30-45 minutos (incluyendo descargas)
**Dificultad**: Media
**Requisitos de red**: Alta (primera vez)
