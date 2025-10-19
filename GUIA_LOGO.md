# 🎨 GUÍA: Agregar Logo a la Aplicación

## 📋 OPCIÓN 1: Automática (RECOMENDADO)

### Paso 1: Guardar tu logo

Guarda tu logo (la imagen que me mostraste) como:
```
logo_original.png
```

En la carpeta:
```
C:\Users\jqnfu\Desktop\Proyectos\ClimaConvento\
```

### Paso 2: Ejecutar script

Abre PowerShell en la carpeta del proyecto y ejecuta:
```powershell
python procesar_logo.py
```

### Paso 3: Listo

El script creará automáticamente estos archivos:
- ✅ `app/src/main/res/mipmap-mdpi/ic_launcher.png` (48x48 px)
- ✅ `app/src/main/res/mipmap-hdpi/ic_launcher.png` (72x72 px)
- ✅ `app/src/main/res/mipmap-xhdpi/ic_launcher.png` (96x96 px)
- ✅ `app/src/main/res/mipmap-xxhdpi/ic_launcher.png` (144x144 px)
- ✅ `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` (192x192 px)

---

## 📋 OPCIÓN 2: Manual

### Si prefieres hacerlo manualmente:

1. **Redimensiona tu logo** a estos tamaños:
   - 48x48 px
   - 72x72 px
   - 96x96 px
   - 144x144 px
   - 192x192 px

2. **Guárdalos** como `ic_launcher.png` en cada carpeta:
   ```
   app/src/main/res/mipmap-mdpi/ic_launcher.png
   app/src/main/res/mipmap-hdpi/ic_launcher.png
   app/src/main/res/mipmap-xhdpi/ic_launcher.png
   app/src/main/res/mipmap-xxhdpi/ic_launcher.png
   app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
   ```

---

## 📋 OPCIÓN 3: Android Studio Asset Studio (MÁS FÁCIL)

### En Android Studio:

1. **Click derecho** en `app/src/main/res`
2. Selecciona: **New → Image Asset**
3. En "Icon Type": selecciona **Launcher Icons (Adaptive and Legacy)**
4. En "Path": click en la carpeta y selecciona tu logo
5. Ajusta el tamaño si es necesario
6. Click en **Next** → **Finish**

✅ Android Studio creará automáticamente todos los tamaños

---

## ✅ Verificar que funcionó

Después de agregar el logo:

1. **Sync Gradle:**
   ```
   File → Sync Project with Gradle Files
   ```

2. **Clean & Rebuild:**
   ```
   Build → Clean Project
   Build → Rebuild Project
   ```

3. **Run App:**
   ```
   Run → Run 'app' ▶️
   ```

4. **Verifica:**
   - El logo aparecerá en la pantalla de inicio
   - En el cajón de aplicaciones
   - En la barra de navegación

---

## 🎯 Recomendación

**USA OPCIÓN 3** (Android Studio Asset Studio) - Es la más fácil y rápida:
- ✅ No requiere redimensionar manualmente
- ✅ Android Studio lo hace todo
- ✅ Crea versiones adaptativas automáticamente
- ✅ 2 minutos total

---

## 💡 Consejos para el Logo

- **Formato:** PNG con fondo transparente
- **Tamaño original:** Mínimo 512x512 px (recomendado 1024x1024 px)
- **Forma:** Cuadrado
- **Colores:** Simples y contrastantes
- **Detalles:** No demasiado pequeños (se verá en 48x48 px)

---

## ❓ Si tienes problemas

Si el logo no aparece después de Sync Gradle:
1. Verifica que los archivos estén en las carpetas correctas
2. Los nombres deben ser exactamente `ic_launcher.png`
3. Ejecuta: **File → Invalidate Caches → Invalidate and Restart**

---

**¿Cuál opción prefieres?**
- 🤖 **Opción 1:** Guarda logo como `logo_original.png` y ejecuta script
- 👆 **Opción 2:** Copia manualmente en 5 carpetas
- ⭐ **Opción 3:** Usa Android Studio Asset Studio (RECOMENDADO)
