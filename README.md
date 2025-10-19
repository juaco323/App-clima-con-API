# 🌡️ Clima Convento

**Aplicación Android para monitoreo de temperatura y clima en tiempo real de 5 ubicaciones favoritas en Chile.**

---

## 📱 Características

- ✅ **Clima en Tiempo Real** - Temperatura, viento, humedad y descripción
- ✅ **5 Ubicaciones Favoritas** - Convento, Campo Alegre, Santo Domingo, Rengo, Rancagua
- ✅ **Mapa Interactivo** - Visualización de temperaturas con colores según temperatura
- ✅ **Pronóstico 5 Días** - Temperatura máxima, mínima y condiciones meteorológicas
- ✅ **Alertas Automáticas** - Notificaciones para temperaturas extremas (≥25°C o ≤4°C)
- ✅ **Diseño Minimalista** - Interfaz intuitiva y fácil de usar
- ✅ **Monitoreo en Background** - Actualización automática cada hora

---

## 🎯 Requisitos

- **Android:** 7.0 (API 24) o superior
- **Conexión:** Internet (WiFi o datos móviles)
- **Permisos:** Internet, Notificaciones

---

## 🗺️ Ubicaciones Monitoreadas

| Ubicación | Latitud | Longitud |
|-----------|---------|----------|
| Convento | -33.4489 | -70.6693 |
| Campo Alegre | -33.871788 | -71.6968129 |
| Santo Domingo | -33.6528 | -71.6139 |
| Rengo | -34.4069 | -70.8639 |
| Rancagua | -34.1708 | -70.7406 |

---

## 🔧 Tecnologías

- **Lenguaje:** Kotlin 1.9.0
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **API Clima:** OpenWeatherMap (1,000 llamadas/día gratis)
- **Mapas:** MapBox SDK 11.0.0
- **Base de Datos:** Room 2.6.1 (SQLite)
- **Networking:** Retrofit 2.9.0 + OkHttp 4.11.0
- **Async:** Kotlin Coroutines 1.7.3
- **Background:** WorkManager 2.9.0
- **UI:** Material Design 3 (1.11.0)

---

## 🚀 Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/juaco323/App-clima-con-API.git
cd ClimaConvento
```

### 2. Abrir en Android Studio
```
File → Open → Seleccionar carpeta del proyecto
```

### 3. Sync Gradle
```
File → Sync Project with Gradle Files
```
⏱️ Espera 1-2 minutos

### 4. Compilar y Ejecutar
```
Build → Clean Project
Build → Rebuild Project
Run → Run 'app' ▶️
```
⏱️ Primera compilación: 5-10 minutos

---

## 📂 Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/climaconvento/
│   │   ├── MainActivity.kt              # Pantalla principal con mapa
│   │   ├── LocationDetailActivity.kt    # Detalles y pronóstico
│   │   ├── NotificationsActivity.kt     # Historial de alertas
│   │   ├── data/
│   │   │   ├── api/                     # Retrofit y API service
│   │   │   ├── local/                   # Room database
│   │   │   ├── model/                   # Data classes
│   │   │   └── repository/              # Repository pattern
│   │   ├── ui/
│   │   │   ├── adapter/                 # RecyclerView adapters
│   │   │   └── map/                     # MapBox manager
│   │   ├── viewmodel/                   # ViewModels
│   │   ├── worker/                      # Background tasks
│   │   ├── notifications/               # Notification system
│   │   └── utils/                       # Utilities
│   └── res/
│       ├── layout/                      # XML layouts
│       ├── drawable/                    # Icons
│       ├── values/                      # Strings, colors, themes
│       └── menu/                        # Navigation menu
```

---

## 🎨 Agregar Logo Personalizado

Ver guía completa en: **[GUIA_LOGO.md](GUIA_LOGO.md)**

**Método Recomendado (Android Studio Asset Studio):**
1. Click derecho en `app/src/main/res`
2. **New → Image Asset**
3. Selecciona tu logo (PNG recomendado, 512x512 px mínimo)
4. **Next → Finish**
5. Sync Gradle

---

## 📊 Funcionalidades Detalladas

### 🏠 Pantalla Principal
- Mapa interactivo de Chile con 5 ubicaciones
- Marcadores con temperatura actual
- Colores según temperatura:
  - 🔵 **Azul:** Frío (<15°C)
  - 🟡 **Amarillo:** Templado (15-25°C)
  - 🔴 **Rojo:** Calor (>25°C)
- Click en marcador muestra detalles
- Menú lateral para navegación rápida

### 📍 Vista de Detalles
- Temperatura actual en °C
- Sensación térmica
- Velocidad del viento en m/s
- Humedad y presión
- Descripción del clima en español
- Pronóstico de 5 días:
  - Fecha
  - Temperatura máx/mín
  - Condiciones esperadas

### 🔔 Sistema de Alertas
**Umbrales:**
- 🔥 **Alta:** Temperatura ≥ 25°C
- ❄️ **Baja:** Temperatura ≤ 4°C

**Características:**
- Monitoreo automático cada hora
- Notificaciones push con sonido
- Cooldown de 6 horas entre alertas
- Historial de 24 horas
- Auto-eliminación de alertas antiguas

### 📱 Historial de Notificaciones
- Ver todas las alertas de las últimas 24 horas
- Marcar como leída
- Eliminar individualmente
- Indicador de alertas no leídas

### 🍔 Menú Lateral
- Acceso rápido a 5 ubicaciones
- Botón de actualización manual
- Ver notificaciones
- Diseño Material Design 3

---

## 🌐 API de Clima - OpenWeatherMap

**Características:**
- ✅ Confiable (99.9% uptime)
- ✅ Respuesta rápida (<1 segundo)
- ✅ 1,000 llamadas gratuitas/día
- ✅ Datos en tiempo real
- ✅ Pronóstico de 5 días

**Datos incluidos:**
- Temperatura (°C)
- Sensación térmica (°C)
- Velocidad del viento (m/s)
- Humedad (%)
- Presión atmosférica (hPa)
- Descripción en español
- Icono del clima

**Actualización:**
- Automática: Cada hora
- Manual: Botón de actualizar en menú

---

## 🗺️ MapBox - Mapa Interactivo

**Funcionalidades:**
- Mapa base de Chile
- Marcadores personalizados con temperatura
- Colores dinámicos según temperatura
- Zoom y navegación táctil
- Centrado automático en ubicaciones
- Optimizado para dispositivos móviles

---

## 💾 Base de Datos Local (Room)

**Almacena:**
- Alertas de temperatura
- Timestamp de cada alerta
- Ubicación afectada
- Tipo de alerta (alta/baja)
- Estado (leída/no leída)

**Ventajas:**
- Persistencia de datos
- Acceso sin internet al historial
- Observación reactiva con Flow
- Auto-limpieza de datos antiguos

---

## ⚙️ Configuraciones Personalizables

### Cambiar Umbrales de Temperatura

Editar `WeatherMonitorWorker.kt`:
```kotlin
companion object {
    const val HIGH_TEMP_THRESHOLD = 25.0  // °C
    const val LOW_TEMP_THRESHOLD = 4.0    // °C
}
```

### Cambiar Frecuencia de Monitoreo

Editar `MainActivity.kt`:
```kotlin
val workRequest = PeriodicWorkRequestBuilder<WeatherMonitorWorker>(
    1, TimeUnit.HOURS  // Cambiar intervalo aquí
).build()
```

### Agregar Más Ubicaciones

Editar `Location.kt`:
```kotlin
val FAVORITE_LOCATIONS = listOf(
    Location("Convento", "Convento", -33.4489, -70.6693),
    Location("NuevaCiudad", "Nueva Ciudad", latitud, longitud),
    // ... más ubicaciones
)
```

También agregar en `menu/drawer_menu.xml`:
```xml
<item
    android:id="@+id/nav_nueva_ciudad"
    android:icon="@drawable/ic_location"
    android:title="Nueva Ciudad" />
```

---

## 📱 Compatibilidad

- **Mínimo:** Android 7.0 (Nougat - API 24)
- **Máximo:** Android 14.0 (API 34)
- **Orientación:** Solo vertical (portrait)
- **Idioma:** Español
- **Dispositivos:** Smartphones y tablets

---

## 🐛 Solución de Problemas

### La app no muestra temperaturas
1. ✅ Verifica conexión a Internet
2. ✅ Espera 10-15 segundos (primera carga)
3. ✅ Presiona botón "Actualizar" en menú
4. ✅ Revisa Logcat en Android Studio

### Errores de compilación
```
File → Invalidate Caches → Invalidate and Restart
Build → Clean Project
Build → Rebuild Project
```

### El logo no aparece
1. ✅ Verifica archivos en carpetas `mipmap-*`
2. ✅ Nombres deben ser exactamente `ic_launcher.png`
3. ✅ Sync Gradle después de agregar
4. ✅ Limpia proyecto: Build → Clean Project

### Notificaciones no funcionan
1. ✅ Verifica permisos de notificaciones en configuración del dispositivo
2. ✅ Comprueba que WorkManager esté configurado
3. ✅ Revisa que la temperatura supere los umbrales

### El mapa no carga
1. ✅ Verifica token de MapBox en `strings.xml`
2. ✅ Comprueba conexión a Internet
3. ✅ Revisa permisos en `AndroidManifest.xml`

---

## 📄 Documentación Adicional

- **[GUIA_COMPILACION.md](GUIA_COMPILACION.md)** - Guía detallada de compilación
- **[GUIA_LOGO.md](GUIA_LOGO.md)** - Cómo agregar logo personalizado
- **[CONFIGURACION_MAPBOX.md](CONFIGURACION_MAPBOX.md)** - Configuración de MapBox
- **[NOTAS_TECNICAS.md](NOTAS_TECNICAS.md)** - Detalles técnicos de implementación
- **[RESUMEN.md](RESUMEN.md)** - Resumen ejecutivo del proyecto
- **[CHECKLIST.md](CHECKLIST.md)** - Lista de verificación pre-compilación
- **[EJECUTAR_APP_AHORA.md](EJECUTAR_APP_AHORA.md)** - Guía rápida de ejecución
- **[RESUMEN_EJECUTIVO.md](RESUMEN_EJECUTIVO.md)** - Resumen de migración a OpenWeatherMap

---

## 📈 Métricas de Uso

### Consumo de Recursos
- **RAM:** ~80-120 MB durante uso activo
- **Almacenamiento:** ~15-20 MB instalada
- **Batería:** Bajo impacto (verificación horaria en background)
- **Datos móviles:** ~1-2 MB por día (~50 KB por actualización)

### Llamadas a la API
- **Automáticas:** ~24 llamadas/día (1 por hora)
- **Manuales:** Según uso del usuario
- **Total estimado:** ~120 llamadas/día (5 ubicaciones × 24 horas)
- **Límite gratuito:** 1,000 llamadas/día ✅

---

## 🔐 Seguridad y Privacidad

- ✅ No se recopilan datos personales
- ✅ No se requiere registro de usuario
- ✅ No se comparten datos con terceros
- ✅ Ubicaciones fijas (no GPS del usuario)
- ✅ API keys protegidas en recursos locales
- ✅ Comunicaciones HTTPS encriptadas

---

## 📞 Soporte y Contacto

**Autor:** juaco323  
**GitHub:** [@juaco323](https://github.com/juaco323)  
**Repositorio:** [App-clima-con-API](https://github.com/juaco323/App-clima-con-API)

**Para reportar problemas:**
1. Abre un **Issue** en GitHub
2. Describe el problema detalladamente
3. Incluye logs de Logcat si es posible
4. Especifica versión de Android

---

## 🙏 Agradecimientos

- **OpenWeatherMap** - Por proporcionar API de clima gratuita y confiable
- **MapBox** - Por el SDK de mapas interactivos
- **Material Design** - Por los componentes UI
- **Comunidad Android** - Por las bibliotecas de código abierto

---

## 📜 Licencia

Este proyecto es de código abierto y está disponible bajo la **Licencia MIT**.

---

## 🎯 Versión

**Versión actual:** 1.0  
**Fecha de lanzamiento:** Octubre 2025  
**Estado:** ✅ Estable y Funcionando

---

## 🚀 Próximas Mejoras (Roadmap)

### Versión 1.1
- [ ] Widget de pantalla de inicio
- [ ] Modo oscuro (Dark Mode)
- [ ] Gráficos de temperatura histórica
- [ ] Notificaciones personalizables

### Versión 1.2
- [ ] Más ubicaciones configurables por el usuario
- [ ] Exportación de datos a CSV
- [ ] Compartir clima en redes sociales
- [ ] Índice UV y calidad del aire

### Versión 2.0
- [ ] Predicción con Machine Learning
- [ ] Múltiples idiomas (inglés, portugués)
- [ ] Widgets avanzados
- [ ] Integración con Android Auto

---

## 💡 Cómo Contribuir

Las contribuciones son bienvenidas. Para contribuir:

1. **Fork** el repositorio
2. Crea una **rama** para tu feature:
   ```bash
   git checkout -b feature/MiNuevaCaracteristica
   ```
3. **Commit** tus cambios:
   ```bash
   git commit -m 'Agrega nueva característica X'
   ```
4. **Push** a la rama:
   ```bash
   git push origin feature/MiNuevaCaracteristica
   ```
5. Abre un **Pull Request**

---

## ❓ FAQ (Preguntas Frecuentes)

### ¿La app funciona sin Internet?
No, requiere conexión activa para obtener datos del clima. El historial de alertas sí está disponible offline.

### ¿Puedo agregar mi propia ubicación?
Sí, edita `Location.kt` y agrega las coordenadas de tu ubicación.

### ¿Cuántos datos consume?
Aproximadamente 1-2 MB por día con actualizaciones automáticas.

### ¿Funciona en tablets?
Sí, la app es responsive y funciona en smartphones y tablets.

### ¿Puedo cambiar los umbrales de temperatura?
Sí, edita `WeatherMonitorWorker.kt` y cambia los valores de `HIGH_TEMP_THRESHOLD` y `LOW_TEMP_THRESHOLD`.

### ¿La API es gratis?
Sí, OpenWeatherMap ofrece 1,000 llamadas gratuitas por día, suficiente para esta app.

---

🌡️ **¡Disfruta monitoreando el clima de tus ubicaciones favoritas!** ☀️🌧️❄️⛅

---

**Última actualización:** 19 de Octubre, 2025