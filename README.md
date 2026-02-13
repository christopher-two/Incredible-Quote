# 🎨 Template de Proyecto Android - Configuración Completa

Este es un template profesional para desarrollo de aplicaciones Android con **Jetpack Compose**, **Arquitectura MVI**, **Koin**, y muchas otras tecnologías modernas preconfiguradas.

---

## 🚀 Inicio Rápido (5 minutos)

### Para crear un nuevo proyecto desde este template:

```bash
# 1. Clonar o descargar el template
git clone <tu-repo-template> MiNuevoProyecto
cd MiNuevoProyecto

# 2. Ejecutar el inicializador
./Scripts/init-project.sh

# 3. Seguir las instrucciones interactivas
# Se te pedirá: nombre del proyecto, package, application ID, nombre visible

# 4. Sincronizar con Gradle
./gradlew clean build
```

**📖 Documentación completa**: 
- [Guía de Inicialización](Docs/INIT_TEMPLATE.md) - Documentación detallada
- [Guía Rápida](Docs/QUICK_START.md) - Referencia rápida con ejemplos

---

## ✨ Características del Template

### 🏗️ Arquitectura
- ✅ **MVI (Model-View-Intent)** - Arquitectura unidireccional
- ✅ **Clean Architecture** - Separación por capas
- ✅ **SOLID Principles** - Código mantenible y escalable
- ✅ **Generador de Features** - Crea features MVI automáticamente

### 🎨 UI/UX
- ✅ **Jetpack Compose** - UI declarativa moderna
- ✅ **Material Design 3** - Componentes actualizados
- ✅ **Tema Dinámico** - Soporte completo de Material You
- ✅ **Modo Oscuro** - Implementación completa
- ✅ **SplashScreen API** - Nativo de Android 12+
- ✅ **Edge-to-Edge** - Experiencia inmersiva

### 🔧 Tecnologías Incluidas
- ✅ **Koin** - Inyección de dependencias
- ✅ **Room** - Base de datos local
- ✅ **DataStore** - Preferencias modernas
- ✅ **Ktor** - Cliente HTTP
- ✅ **Navigation 3** - Navegación type-safe
- ✅ **Coil** - Carga de imágenes
- ✅ **WorkManager** - Tareas en background
- ✅ **Kotlinx Serialization** - Serialización JSON
- ✅ **Firebase** (Opcional) - Auth, Firestore, Vertex AI

### 🛡️ Calidad y Seguridad
- ✅ **ProGuard** - Ofuscación y optimización
- ✅ **Type-Safe Navigation** - Navegación segura
- ✅ **Coroutines** - Programación asíncrona
- ✅ **StateFlow** - Gestión de estado reactivo
- ✅ **Biometric Auth** - Autenticación biométrica

### 📚 Documentación
- ✅ **Arquitectura MVI** - Guía completa
- ✅ **Firebase Setup** - Configuración paso a paso
- ✅ **ProGuard** - Mejores prácticas
- ✅ **Sistema de Temas** - Personalización
- ✅ **Dependencias** - Catálogo completo

---

## 📁 Estructura del Proyecto

```
app/src/main/java/<tu.package>/
├── core/                      # Funcionalidades core
│   ├── data/                  # Implementaciones de datos
│   │   └── local/            # Base de datos Room
│   └── ui/                    # Componentes UI reutilizables
│       └── theme/            # Sistema de temas
├── feature/                   # Características por módulo
│   ├── auth/                 # Autenticación
│   │   ├── data/            # Repositorios y fuentes de datos
│   │   ├── domain/          # Modelos y casos de uso
│   │   └── presentation/    # ViewModels y UI
│   └── settings/            # Configuración
│       ├── data/
│       ├── domain/
│       └── presentation/
├── di/                       # Inyección de dependencias (Koin)
│   ├── AppModule.kt
│   ├── DatabaseModule.kt
│   ├── NetworkModule.kt
│   └── ViewModelModule.kt
├── main/                     # Pantalla principal
│   ├── MainState.kt
│   ├── MainAction.kt
│   └── MainViewModel.kt
├── MainActivity.kt           # Actividad principal
└── MainApp.kt               # Clase Application
```

---

## 🎯 Sistema de Inicialización

### ¿Por qué este sistema?

En lugar de renombrar manualmente carpetas y buscar/reemplazar en múltiples archivos, el sistema de inicialización:

1. **Automatiza todo** - Un solo comando configura todo el proyecto
2. **Es interactivo** - Te guía paso a paso
3. **Es seguro** - Opción de backup antes de proceder
4. **Es inteligente** - Valida nombres y previene errores
5. **Es completo** - Actualiza Gradle, XML, Kotlin, y estructura de packages

### ¿Qué se configura?

- ✅ Nombre del proyecto en `settings.gradle.kts`
- ✅ Namespace y Application ID en `app/build.gradle.kts`
- ✅ Package declarations en todos los archivos Kotlin
- ✅ Import statements en todos los archivos
- ✅ Temas XML (Theme.YourApp)
- ✅ Nombre visible de la app en `strings.xml`
- ✅ Reorganización completa de la estructura de packages

---

## 📖 Documentación Completa

| Documento | Descripción |
|-----------|-------------|
| [🚀 Inicialización del Template](Docs/INIT_TEMPLATE.md) | Guía completa de uso del sistema de inicialización |
| [⚡ Generador de Features](Docs/FEATURE_GENERATOR.md) | Generación automática de features MVI |
| [🏗️ Arquitectura MVI](Docs/ARQUITECTURA_MVI.md) | Explicación de la arquitectura implementada |
| [🔥 Firebase Setup](Docs/FIREBASE_SETUP.md) | Configuración opcional de Firebase |
| [🛡️ ProGuard](Docs/PROGUARD.md) | Configuración de ofuscación |
| [🎨 Sistema de Temas](Docs/THEME_SYSTEM.md) | Personalización de temas |
| [📦 Dependencias](Docs/DEPENDENCIAS.md) | Catálogo de librerías incluidas |

---

## 🔧 Requisitos del Sistema

- **Android Studio**: Iguana (2023.2.1) o superior
- **JDK**: 11 o superior
- **Gradle**: 8.7 (incluido en el wrapper)
- **Min SDK**: 29 (Android 10)
- **Target SDK**: 36 (Android 14)
- **Compile SDK**: 36 (Android 14)
- **Kotlin**: 2.1.0

### Para ejecutar el inicializador:

- **Kotlin Compiler** o **Android Studio/IntelliJ IDEA**

---

## 🚀 Uso del Template

### 1️⃣ Crear un Nuevo Proyecto

```bash
# Opción A: Clonar como template
git clone <url-del-template> MiNuevoProyecto
cd MiNuevoProyecto
rm -rf .git  # Opcional: remover historial de git del template

# Opción B: Usar GitHub Template
# Click en "Use this template" en GitHub
```

### 2️⃣ Inicializar el Proyecto

```bash
chmod +x Scripts/init-project.sh
./Scripts/init-project.sh
```

Sigue las instrucciones interactivas:

```
Nombre del proyecto: MyApp
Package base: com.mycompany.myapp
Application ID: [com.mycompany.myapp]
Nombre visible de la app: [MyApp]
```

### 3️⃣ Sincronizar y Construir

```bash
# Limpiar
./gradlew clean

# Construir
./gradlew build

# O desde Android Studio
File → Sync Project with Gradle Files
```

### 4️⃣ ¡Comienza a Desarrollar!

El proyecto está listo. Puedes:

- **Generar nuevas features automáticamente** con `./Scripts/generate-feature.sh` ([Ver guía](Docs/FEATURE_GENERATOR.md))
- Modificar el tema en `core/ui/theme/`
- Configurar Firebase (opcional) según [esta guía](Docs/FIREBASE_SETUP.md)
- Personalizar el SplashScreen
- Agregar más dependencias desde el catálogo `libs.versions.toml`

---

## 🎨 Personalización Post-Inicialización

### Cambiar el Ícono de la App

1. Genera íconos en [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/)
2. Reemplaza los archivos en `app/src/main/res/mipmap-*/`

### Configurar Firebase (Opcional)

Ver [Guía de Firebase](Docs/FIREBASE_SETUP.md)

### Personalizar el Tema

Ver [Sistema de Temas](Docs/THEME_SYSTEM.md)

### Agregar Nuevas Dependencias

Edita `gradle/libs.versions.toml` y agrega en la sección correspondiente.

---

## 📦 Dependencias Principales

### Core
- **Kotlin**: 2.1.0
- **Compose BOM**: 2025.01.00
- **Android Gradle Plugin**: 8.8.1

### UI
- **Material 3**: Latest
- **Navigation Compose 3**: 3.0.0-alpha11
- **Coil**: 3.0.4
- **Material Kolor**: 2.1.0

### Architecture
- **Koin**: 4.1.0
- **Room**: 2.6.1
- **DataStore**: 1.1.1
- **Ktor**: 3.0.3

### Testing
- **JUnit**: 4.13.2
- **Espresso**: Latest
- **Turbine**: 1.2.0

Ver catálogo completo en [Dependencias](Docs/DEPENDENCIAS.md)

---

## 🛠️ Scripts Incluidos

| Script | Descripción |
|--------|-------------|
| `Scripts/init-project.sh` | 🚀 Inicializa el template (Bash wrapper) |
| `Scripts/init-project.main.kts` | 🚀 Script principal de inicialización (Kotlin) |
| `Scripts/verify-proguard.sh` | 🛡️ Verifica la configuración de ProGuard |

---

## 🧪 Testing

```bash
# Tests unitarios
./gradlew test

# Tests instrumentados
./gradlew connectedAndroidTest

# Todos los tests
./gradlew check
```

---

## 🏗️ Build Variants

### Debug
- Sin ofuscación
- Logs habilitados
- Depuración fácil

### Release
- ProGuard habilitado
- Recursos optimizados
- APK reducido ~70%

```bash
# Build debug
./gradlew assembleDebug

# Build release
./gradlew assembleRelease
```

---

## 🤝 Contribuir

### Reportar Problemas

Si encuentras bugs o tienes sugerencias:

1. Abre un Issue
2. Describe el problema o mejora
3. Incluye pasos para reproducir (si aplica)

### Proponer Mejoras

1. Fork el repositorio
2. Crea una rama para tu feature
3. Implementa los cambios
4. Envía un Pull Request

---

## 📄 Licencia

[Especificar tu licencia aquí]

---

## 🙏 Créditos

Este template incluye y se basa en:

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Koin](https://insert-koin.io/)
- [Ktor](https://ktor.io/)
- [Material Kolor](https://github.com/jordond/materialkolor)
- Y muchas otras librerías open source

---

## 📞 Soporte

- 📚 **Documentación**: Ver carpeta `Docs/`
- 💬 **Issues**: [GitHub Issues]
- 📧 **Email**: [Tu email]

---

## 🎉 Características Destacadas

### ✅ Listo para Producción
- Configuración completa de ProGuard
- Optimización de recursos
- Build variants configurados

### ✅ Desarrollo Moderno
- Compose más reciente
- Kotlin 2.1.0
- Android 14 (API 36)

### ✅ Experiencia de Usuario
- Material You (Tema Dinámico)
- SplashScreen nativo
- Edge-to-Edge
- Modo oscuro

### ✅ Arquitectura Sólida
- MVI Pattern
- Clean Architecture
- SOLID Principles
- Type-Safe Navigation

### ✅ Fácil Mantenimiento
- Código bien documentado
- Estructura clara
- Separación de concerns
- Testing preparado

---

**¡Empieza a construir tu próxima gran app! 🚀**

