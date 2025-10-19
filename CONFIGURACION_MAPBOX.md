# CONFIGURACIÓN IMPORTANTE - MAPBOX

## ⚠️ ACCIÓN REQUERIDA ANTES DE COMPILAR

La aplicación utiliza MapBox para mostrar el mapa interactivo de temperatura. 
Para que funcione correctamente, debes configurar un token de acceso de MapBox.

## Pasos para configurar MapBox:

### 1. Crear cuenta en MapBox
- Visita: https://account.mapbox.com/auth/signup/
- Crea una cuenta gratuita
- La cuenta gratuita incluye hasta 50,000 cargas de mapa al mes (suficiente para uso personal)

### 2. Obtener Access Token
- Una vez logeado, ve a: https://account.mapbox.com/
- En la sección "Access tokens", verás tu token predeterminado
- O crea uno nuevo haciendo clic en "Create a token"
- Copia el token (se ve similar a: `pk.eyJ1IjoiZXhhbXBsZSIsImEiOiJja...`)

### 3. Configurar en la aplicación

Opción A - Agregar a strings.xml (Recomendado para desarrollo):

Edita el archivo: `app/src/main/res/values/strings.xml`

Agrega esta línea:
```xml
<string name="mapbox_access_token">TU_TOKEN_AQUI</string>
```

Luego en `app/build.gradle`, agrega en defaultConfig:
```gradle
defaultConfig {
    ...
    manifestPlaceholders = [
        MAPBOX_ACCESS_TOKEN: "@string/mapbox_access_token"
    ]
}
```

Opción B - Agregar directamente al gradle.properties:

Edita: `gradle.properties`

Agrega:
```properties
MAPBOX_ACCESS_TOKEN=tu_token_aqui
```

Luego en `app/build.gradle`:
```gradle
defaultConfig {
    ...
    manifestPlaceholders = [
        MAPBOX_ACCESS_TOKEN: project.properties['MAPBOX_ACCESS_TOKEN']
    ]
}
```

### 4. Agregar al AndroidManifest.xml

Dentro de la etiqueta `<application>`, agrega:
```xml
<meta-data
    android:name="com.mapbox.token"
    android:value="${MAPBOX_ACCESS_TOKEN}" />
```

## Alternativa: Usar Google Maps en lugar de MapBox

Si prefieres no usar MapBox, puedes modificar el código para usar Google Maps:

1. Cambiar dependencias en `app/build.gradle`:
```gradle
implementation 'com.google.android.gms:play-services-maps:18.2.0'
```

2. Modificar `TemperatureMapManager.kt` para usar Google Maps API

3. Obtener API Key de Google Cloud Console

## Notas Importantes:

- **NO compartas tu token públicamente** (especialmente en repositorios públicos)
- Si usas control de versiones (Git), agrega `local.properties` y archivos con tokens al `.gitignore`
- Para producción, considera usar variables de entorno o servicios de gestión de secretos
- El token gratuito de MapBox es suficiente para esta aplicación de uso personal

## Solución sin MapBox (Modo Simplificado):

Si no deseas usar mapas interactivos, puedes simplificar la aplicación:

1. Comenta o elimina la inicialización del mapa en `MainActivity.kt`:
```kotlin
// setupMap()
```

2. Oculta el contenedor del mapa en `activity_main.xml`:
```xml
<FrameLayout
    android:id="@+id/map_container"
    android:visibility="gone"
    ...
```

3. La app funcionará solo con la lista de ubicaciones del menú lateral

## Verificación:

Después de configurar, ejecuta la app:
- Deberías ver un mapa centrado en Chile
- Con 5 marcadores en las ubicaciones favoritas
- Los marcadores deberían cambiar de color según la temperatura

Si ves un error de mapa en blanco:
1. Verifica que el token esté correctamente configurado
2. Revisa Logcat en Android Studio para errores específicos
3. Asegúrate de tener conexión a Internet

---

**Para más información sobre MapBox:**
- Documentación: https://docs.mapbox.com/android/maps/guides/
- Precios: https://www.mapbox.com/pricing
- Ejemplos: https://docs.mapbox.com/android/maps/examples/
