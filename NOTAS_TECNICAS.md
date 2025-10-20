# NOTAS TÉCNICAS Y CONFIGURACIÓN

## 📍 Coordenadas de Ubicaciones

Las coordenadas utilizadas en la aplicación son aproximadas. Para mayor precisión, puedes ajustarlas:

### Ubicaciones Actuales (en Location.kt):

```kotlin
Location("Convento", "Convento", -33.776986, -71.5966961)
Location("Campo Alegre", "Campo Alegre", -33.5, -70.7)
Location("Santo Domingo", "Santo Domingo", -33.6528, -71.6139)
Location("Rengo", "Rengo", -34.4069, -70.8639)
Location("Rancagua", "Rancagua", -34.1708, -70.7406)
```

### Cómo obtener coordenadas exactas:

1. **Google Maps**:
   - Buscar la ubicación
   - Clic derecho en el punto exacto
   - Seleccionar las coordenadas que aparecen
    - Formato: `-33.776986, -71.5966961`

2. **Otras herramientas**:
   - https://www.latlong.net/
   - https://gps-coordinates.org/

3. **Actualizar en el código**:
   Editar: `app/src/main/java/com/climaconvento/data/model/Location.kt`

## 🌡️ Configuración de Umbrales de Temperatura

### Umbrales de Alertas (WeatherMonitorWorker.kt):

```kotlin
HIGH_TEMP_THRESHOLD = 25.0  // °C - Temperatura alta
LOW_TEMP_THRESHOLD = 4.0    // °C - Temperatura baja
ALERT_COOLDOWN_HOURS = 6    // Horas entre alertas similares
```

**Recomendaciones según región**:

| Región | Temp. Alta (°C) | Temp. Baja (°C) |
|--------|-----------------|-----------------|
| Zona Norte | 30 | 10 |
| Zona Central | 25 | 4 |
| Zona Sur | 20 | 0 |

### Colores del Mapa (TemperatureColorHelper.kt):

```kotlin
≤ 4°C   → Azul brillante (#2196F3)
≤ 10°C  → Azul claro (#64B5F6)
≤ 15°C  → Azul muy claro (#81D4FA)
≤ 20°C  → Amarillo claro (#FFF59D)
≤ 25°C  → Amarillo (#FFD54F)
≤ 30°C  → Naranja (#FF9800)
> 30°C  → Rojo (#F44336)
```

**Para modificar**:
Editar: `app/src/main/java/com/climaconvento/utils/TemperatureColorHelper.kt`

## 🔄 Frecuencia de Actualización

### Actualización del Clima:

1. **Actualización automática**: Cada 1 hora (configurado en WorkManager)
2. **API de Weather Unlocked**: Actualiza datos cada hora
3. **Actualización manual**: Botón de refresh en el menú

### Modificar frecuencia del WorkManager:

En `MainActivity.kt`, método `setupWorkManager()`:

```kotlin
val workRequest = PeriodicWorkRequestBuilder<WeatherMonitorWorker>(
    1, TimeUnit.HOURS  // Cambiar aquí (mínimo: 15 minutos)
).build()
```

**Opciones**:
- `15, TimeUnit.MINUTES` - Cada 15 minutos (mínimo permitido por Android)
- `1, TimeUnit.HOURS` - Cada hora (recomendado)
- `2, TimeUnit.HOURS` - Cada 2 horas
- `1, TimeUnit.DAYS` - Una vez al día

### Caché y Persistencia:

- **Alertas**: Se guardan en Room Database (SQLite)
- **Clima actual**: Solo en memoria (LiveData)
- **Duración de alertas**: 24 horas (auto-eliminación)

## 🌐 Configuración de la API

### Weather Unlocked API:

**Credenciales** (incluidas en el código):
- App ID: `021414f8`
- App Key: `fc41f2ec43a577f1dc617965fb2e2d1e`

**Endpoints utilizados**:
1. Clima actual: `GET /api/current/{lat,lon}`
2. Pronóstico: `GET /api/forecast/{lat,lon}`

**Formato de ubicación**: `"latitud,longitud"` (ej: "-33.776986,-71.5966961")

### Límites de la API:

| Límite | Valor |
|--------|-------|
| Llamadas por minuto | 75 |
| Llamadas por día | 25,000 |
| Actualización de datos | Cada hora |
| Pronóstico | 8 días, intervalos de 3h |
| Disponibilidad | 95% |

### Estimación de uso:

Con 5 ubicaciones y actualización cada hora:
- **Por hora**: 5 llamadas (clima actual) = 5 llamadas
- **Por día**: 5 × 24 = 120 llamadas
- **Margen**: 24,880 llamadas disponibles

**Nota**: Muy por debajo del límite diario. ✅

## 🗄️ Base de Datos Local

### Tabla: weather_alerts

```sql
CREATE TABLE weather_alerts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    locationName TEXT NOT NULL,
    temperature REAL NOT NULL,
    alertType TEXT NOT NULL,
    timestamp INTEGER NOT NULL,
    isRead INTEGER NOT NULL DEFAULT 0
)
```

### Operaciones:

1. **Insertar alerta**: Al detectar temperatura fuera de rango
2. **Marcar como leída**: Al abrir notificación
3. **Eliminar**: Manual o automática (>24h)
4. **Consultar**: En tiempo real con Flow

### Acceder a la base de datos (Debug):

1. Android Studio → `App Inspection` → `Database Inspector`
2. O usar adb: `adb shell`
3. Ruta: `/data/data/com.climaconvento/databases/clima_convento_database`

## 🎨 Personalización del Diseño

### Cambiar nombre de la app:

**Archivo**: `app/src/main/res/values/strings.xml`
```xml
<string name="app_name">Tu Nombre Aquí</string>
```

### Cambiar colores principales:

**Archivo**: `app/src/main/res/values/colors.xml`
```xml
<color name="primary_blue">#2196F3</color>  <!-- Cambiar aquí -->
<color name="primary_blue_dark">#1976D2</color>
```

### Cambiar tamaño de fuentes:

**Archivo**: `app/src/main/res/values/themes.xml`
```xml
<style name="TextAppearance.Temperature">
    <item name="android:textSize">48sp</item>  <!-- Ajustar tamaño -->
</style>
```

### Agregar más ubicaciones:

**Archivo**: `app/src/main/java/com/climaconvento/data/model/Location.kt`

1. Agregar a la lista `FAVORITE_LOCATIONS`
2. Agregar entrada en `strings.xml`
3. Agregar item en `drawer_menu.xml`
4. Agregar case en `MainActivity.onNavigationItemSelected()`

## 🔒 Seguridad y Privacidad

### Datos recopilados:

- ❌ **NO** se recopilan datos personales
- ❌ **NO** se requiere registro o login
- ✅ Solo datos meteorológicos públicos
- ✅ Alertas guardadas localmente

### Permisos justificados:

| Permiso | Uso |
|---------|-----|
| INTERNET | Obtener datos del clima de la API |
| ACCESS_NETWORK_STATE | Verificar si hay conexión disponible |
| POST_NOTIFICATIONS | Enviar alertas de temperatura |
| WAKE_LOCK | Permitir actualización en segundo plano |
| RECEIVE_BOOT_COMPLETED | Reiniciar monitoreo tras reinicio |

### Buenas prácticas implementadas:

1. **Sin almacenamiento de credenciales de usuario**
2. **API keys en código** (uso personal, no producción)
3. **HTTPS para todas las llamadas a API**
4. **Validación de entrada de datos**
5. **Manejo de errores robusto**

## 📊 Optimizaciones

### Rendimiento:

1. **Coroutines** para operaciones asíncronas
2. **LiveData** para actualización reactiva de UI
3. **RecyclerView** con DiffUtil para listas eficientes
4. **Room** con índices optimizados
5. **Retrofit** con OkHttp para caché HTTP

### Batería:

1. **WorkManager** con restricciones de batería
2. **Actualizaciones cada hora** (no en tiempo real)
3. **Sin GPS** (coordenadas fijas)
4. **Alertas con cooldown** (evita spam)

### Datos móviles:

1. **Llamadas API mínimas** (5 ubicaciones)
2. **Respuestas JSON compactas**
3. **Sin imágenes o recursos pesados**
4. **Estimado**: < 1 MB por día

## 🧪 Testing y Validación

### Probar alertas manualmente:

En `WeatherMonitorWorker.kt`, cambiar temporalmente:
```kotlin
const val HIGH_TEMP_THRESHOLD = 10.0  // Muy bajo, activará fácil
const val LOW_TEMP_THRESHOLD = 40.0   // Muy alto, activará fácil
```

Luego forzar ejecución del Worker:
```kotlin
// En MainActivity.onCreate()
WorkManager.getInstance(this).enqueueUniqueWork(
    "weather_monitor_test",
    ExistingWorkPolicy.REPLACE,
    OneTimeWorkRequestBuilder<WeatherMonitorWorker>().build()
)
```

### Verificar llamadas a la API:

En `RetrofitClient.kt`, el interceptor ya está configurado para logging:
```kotlin
level = HttpLoggingInterceptor.Level.BODY
```

Ver en Logcat: `D/OkHttp: ...`

## 📱 Compatibilidad

### Versiones de Android soportadas:

| Versión | API Level | Soportado |
|---------|-----------|-----------|
| Android 14 | 34 | ✅ |
| Android 13 | 33 | ✅ |
| Android 12 | 31-32 | ✅ |
| Android 11 | 30 | ✅ |
| Android 10 | 29 | ✅ |
| Android 9 | 28 | ✅ |
| Android 8 | 26-27 | ✅ |
| Android 7 | 24-25 | ✅ |
| Android 6 y menores | ≤23 | ❌ |

### Resoluciones de pantalla:

- ✅ Teléfonos pequeños (4.0" - 5.0")
- ✅ Teléfonos medianos (5.1" - 6.0")
- ✅ Teléfonos grandes (6.1" - 7.0")
- ⚠️ Tablets (funciona pero no optimizado)

## 🚀 Mejoras Futuras Sugeridas

1. **Widget de pantalla principal**
2. **Soporte para modo oscuro**
3. **Gráficos de tendencia de temperatura**
4. **Pronóstico extendido (más de 8 días)**
5. **Compartir clima en redes sociales**
6. **Backup/sincronización de alertas**
7. **Soporte multiidioma completo**
8. **Accesibilidad mejorada (TalkBack)**
9. **Animaciones más fluidas**
10. **Soporte para Wear OS (smartwatch)**

---

**Última actualización**: Octubre 2025  
**Versión de la documentación**: 1.0
