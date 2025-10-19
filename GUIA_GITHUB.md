# 📦 GUÍA: Subir Proyecto a GitHub

**Repositorio:** https://github.com/juaco323/App-clima-con-API  
**Rama:** Version1.0  
**Descripción:** "App simple con valores de temperaturas"

---

## ⚠️ IMPORTANTE: Hacer DESPUÉS de compilar en Android Studio

**NO subir a GitHub hasta:**
1. ✅ Haber agregado el logo
2. ✅ Haber compilado exitosamente
3. ✅ Haber probado que la app funciona
4. ✅ Haber verificado que aparecen las temperaturas

---

## 🚀 PASOS PARA SUBIR A GITHUB

### 1️⃣ Abrir PowerShell en la carpeta del proyecto

```powershell
cd C:\Users\jqnfu\Desktop\Proyectos\ClimaConvento
```

---

### 2️⃣ Verificar estado de Git

```powershell
# Ver si ya está inicializado Git
git status
```

**Si muestra error "not a git repository":**
```powershell
# Inicializar Git
git init
```

---

### 3️⃣ Configurar Git (si es primera vez)

```powershell
git config user.name "juaco323"
git config user.email "tu-email@ejemplo.com"
```

---

### 4️⃣ Crear archivo .gitignore

Asegúrate de tener `.gitignore` con este contenido:

```
# Android
*.iml
.gradle
/local.properties
/.idea/
.DS_Store
/build
/captures
.externalNativeBuild
.cxx
local.properties

# Built application files
*.apk
*.ap_
*.aab

# Files for the ART/Dalvik VM
*.dex

# Java class files
*.class

# Generated files
bin/
gen/
out/

# Gradle files
.gradle/
build/

# Local configuration file
gradle.properties

# Android Studio
*.iml
.idea/workspace.xml
.idea/tasks.xml
.idea/gradle.xml
.idea/assetWizardSettings.xml
.idea/dictionaries
.idea/libraries
.idea/caches

# Keystore files
*.jks
*.keystore

# Python
*.pyc
__pycache__/
*.py[cod]

# Logs
*.log
```

---

### 5️⃣ Agregar archivos al staging area

```powershell
# Ver qué archivos se agregarán
git status

# Agregar todos los archivos
git add .

# Verificar archivos agregados
git status
```

---

### 6️⃣ Hacer commit inicial

```powershell
git commit -m "Version 1.0 - App simple con valores de temperaturas"
```

---

### 7️⃣ Crear rama Version1.0

```powershell
# Crear y cambiar a rama Version1.0
git checkout -b Version1.0

# Verificar que estás en la rama correcta
git branch
```

Deberías ver:
```
  master (o main)
* Version1.0
```

---

### 8️⃣ Conectar con repositorio remoto

```powershell
# Agregar repositorio remoto
git remote add origin https://github.com/juaco323/App-clima-con-API.git

# Verificar conexión
git remote -v
```

Deberías ver:
```
origin  https://github.com/juaco323/App-clima-con-API.git (fetch)
origin  https://github.com/juaco323/App-clima-con-API.git (push)
```

---

### 9️⃣ Subir código a GitHub

```powershell
# Push a la rama Version1.0
git push -u origin Version1.0
```

**Se te pedirá usuario y contraseña de GitHub.**

---

### 🔟 Verificar en GitHub

1. Ve a: https://github.com/juaco323/App-clima-con-API
2. Deberías ver la rama **Version1.0**
3. Click en la rama para verificar archivos
4. La descripción debería decir: "App simple con valores de temperaturas"

---

## 🔐 AUTENTICACIÓN EN GITHUB

### Si te pide credenciales:

**Opción 1: Personal Access Token (Recomendado)**

1. Ve a: https://github.com/settings/tokens
2. Click en "Generate new token" → "Generate new token (classic)"
3. Dale un nombre: "ClimaConvento"
4. Selecciona scope: **repo** (marcar todas las casillas de repo)
5. Click en "Generate token"
6. **COPIA el token** (no lo volverás a ver)
7. Úsalo como contraseña cuando Git te lo pida

**Opción 2: GitHub CLI**

```powershell
# Instalar GitHub CLI
winget install GitHub.cli

# Autenticarse
gh auth login
```

---

## 📝 AGREGAR DESCRIPCIÓN A LA RAMA

### En GitHub Web:

1. Ve a: https://github.com/juaco323/App-clima-con-API
2. Click en la rama **Version1.0**
3. Click en **Settings** de la rama
4. Agregar descripción: "App simple con valores de temperaturas"

### O crear Pull Request con descripción:

1. Ve a: https://github.com/juaco323/App-clima-con-API/pulls
2. Click en **New pull request**
3. Selecciona:
   - Base: `main` (o `master`)
   - Compare: `Version1.0`
4. Título: "Version 1.0"
5. Descripción: "App simple con valores de temperaturas"
6. Click en **Create pull request**

---

## 🔄 COMANDOS ÚTILES PARA FUTURAS ACTUALIZACIONES

### Ver estado del repositorio
```powershell
git status
```

### Agregar cambios
```powershell
git add .
git commit -m "Descripción de cambios"
git push origin Version1.0
```

### Ver historial de commits
```powershell
git log --oneline
```

### Ver ramas
```powershell
git branch -a
```

### Cambiar de rama
```powershell
git checkout nombre-rama
```

### Crear nueva rama
```powershell
git checkout -b nueva-rama
```

---

## 🐛 SOLUCIÓN DE PROBLEMAS

### Error: "Permission denied"
```powershell
# Usar Personal Access Token como contraseña
# Ver sección "Autenticación en GitHub"
```

### Error: "remote origin already exists"
```powershell
# Eliminar origen existente
git remote remove origin

# Agregar nuevamente
git remote add origin https://github.com/juaco323/App-clima-con-API.git
```

### Error: "refusing to merge unrelated histories"
```powershell
git pull origin Version1.0 --allow-unrelated-histories
```

### Subiste archivos incorrectos
```powershell
# Ver archivos en staging
git status

# Quitar archivo específico
git reset HEAD nombre-archivo

# O quitar todos
git reset HEAD .

# Luego agregar solo los correctos
git add archivo1 archivo2
git commit -m "Mensaje"
```

---

## ✅ VERIFICACIÓN FINAL

Después de subir, verifica en GitHub:

```
[✅] Repositorio: https://github.com/juaco323/App-clima-con-API
[✅] Rama: Version1.0 existe
[✅] Archivos subidos correctamente
[✅] README.md se ve correctamente
[✅] No hay archivos sensibles (API keys están OK)
[✅] .gitignore funciona (no hay builds/gradle)
```

---

## 📋 ESTRUCTURA ESPERADA EN GITHUB

```
App-clima-con-API/
├── app/
│   ├── src/
│   └── build.gradle
├── gradle/
├── .gitignore
├── build.gradle
├── settings.gradle
├── README.md
├── GUIA_COMPILACION.md
├── GUIA_LOGO.md
├── CONFIGURACION_MAPBOX.md
├── NOTAS_TECNICAS.md
├── RESUMEN.md
├── CHECKLIST.md
├── EJECUTAR_APP_AHORA.md
├── RESUMEN_EJECUTIVO.md
├── CAMBIOS_REALIZADOS.md
├── GUIA_GITHUB.md
└── procesar_logo.py
```

---

## 🎯 RECORDATORIO

**NO olvides:**
1. ✅ Compilar primero en Android Studio
2. ✅ Probar que funciona
3. ✅ Agregar logo
4. ✅ Luego subir a GitHub

**Orden correcto:**
```
Android Studio → Compilar → Probar → GitHub ✅
GitHub → Android Studio ❌ (INCORRECTO)
```

---

**¿Listo para subir?**

Sigue los pasos 1-9 en orden y tendrás tu proyecto en GitHub en 5 minutos. 🚀
