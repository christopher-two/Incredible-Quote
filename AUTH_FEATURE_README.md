# 🚀 Auth Feature - Onboarding & Authentication

Esta feature implementa un sistema completo de onboarding y autenticación pensado para ser **reutilizable y fácilmente modificable**.

## 📁 Estructura

```
feature/auth/
├── data/
│   └── local/
│       ├── UserPreferencesRepository.kt  # Implementación de persistencia con DataStore
│       └── UserSerializer.kt             # Serializador para DataStore
├── domain/
│   ├── model/
│   │   ├── User.kt                      # Modelo de usuario
│   │   └── OnboardingPage.kt            # Modelo de página de onboarding
│   ├── repository/
│   │   └── UserRepository.kt            # Contrato del repositorio
│   └── usecase/
│       ├── LoginUseCase.kt              # Caso de uso para login
│       └── CheckSessionUseCase.kt       # Caso de uso para verificar sesión
└── presentation/
    ├── components/
    │   ├── OnboardingPageItem.kt        # Componente reutilizable de página
    │   └── HorizontalPagerIndicator.kt  # Indicador de páginas
    ├── AuthRoot.kt                       # Pantalla principal
    ├── AuthViewModel.kt                  # Lógica de negocio
    ├── AuthState.kt                      # Estado de la UI
    └── AuthAction.kt                     # Acciones del usuario
```

## 🎨 Características

- ✅ **Onboarding con imágenes** usando Coil para carga eficiente
- ✅ **HorizontalPager** para navegación entre páginas deslizando
- ✅ **Indicador de páginas** animado
- ✅ **Login simulado** con delay (2 segundos)
- ✅ **Persistencia de sesión** con DataStore
- ✅ **Material Design 3** con shapes y colores del tema
- ✅ **Arquitectura limpia** (Clean Architecture)
- ✅ **MVI Pattern** (Model-View-Intent)
- ✅ **Casos de uso** para lógica de negocio
- ✅ **Inyección de dependencias** con Koin

## 🔧 Cómo personalizar el Onboarding

### 1. Cambiar las páginas del onboarding

Edita el archivo `OnboardingPage.kt`:

```kotlin
object OnboardingDefaults {
    fun getPages(): List<OnboardingPage> {
        return listOf(
            OnboardingPage(
                imageUrl = "TU_URL_DE_IMAGEN",
                title = "Tu título",
                description = "Tu descripción"
            ),
            // Agrega más páginas aquí
        )
    }
}
```

**Tipos de URLs soportadas:**
- URLs remotas: `"https://ejemplo.com/imagen.jpg"`
- Recursos locales: `"file:///android_asset/imagen.jpg"`
- Recursos drawable: Usar `painterResource()` en el componente

### 2. Cambiar el estilo visual

El diseño usa `MaterialTheme`, así que cambiar los colores del tema afectará automáticamente:

```kotlin
// Los bordes redondeados usan:
MaterialTheme.shapes.large

// Los colores usan:
MaterialTheme.colorScheme.primary
MaterialTheme.colorScheme.surfaceContainer
MaterialTheme.colorScheme.onBackground
```

### 3. Modificar el componente de página

Edita `OnboardingPageItem.kt` para cambiar:
- Tamaño de imagen
- Espaciado
- Tipografía
- Animaciones

### 4. Personalizar el login

Edita `LoginUseCase.kt`:

```kotlin
suspend operator fun invoke(): Result<User> {
    // Cambiar delay
    delay(2000) // milisegundos
    
    // Cambiar datos del usuario
    val user = User(
        id = "tu_id",
        name = "Tu nombre",
        email = "tu@email.com",
        // ...
    )
    
    // Implementar tu lógica de autenticación aquí
    // Por ejemplo: llamar a tu API
}
```

## 🔐 Flujo de autenticación

1. Usuario ve el onboarding
2. Usuario hace clic en "Comenzar" o "Omitir"
3. Se ejecuta `LoginUseCase` con delay de 2s
4. El usuario se guarda en `DataStore`
5. `MainViewModel` detecta el cambio de sesión
6. La app navega automáticamente a la pantalla principal

## 🎯 Casos de uso

### LoginUseCase
```kotlin
class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<User>
}
```
**Responsabilidad:** Realizar el proceso de login y guardar la sesión.

### CheckSessionUseCase
```kotlin
class CheckSessionUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<Boolean>
}
```
**Responsabilidad:** Verificar si hay una sesión activa.

## 📦 Componentes reutilizables

### OnboardingPageItem
Muestra una página del onboarding con:
- Imagen con Coil
- Título
- Descripción

**Uso:**
```kotlin
OnboardingPageItem(
    page = OnboardingPage(
        imageUrl = "url",
        title = "Título",
        description = "Descripción"
    )
)
```

### HorizontalPagerIndicator
Indicador de páginas con animación.

**Uso:**
```kotlin
HorizontalPagerIndicator(
    pageCount = 3,
    currentPage = 0
)
```

## 🔄 Estado y acciones

### AuthState
```kotlin
data class AuthState(
    val onboardingPages: List<OnboardingPage>,
    val currentPage: Int,
    val isLoading: Boolean,
    val error: String?,
    val isLastPage: Boolean
)
```

### AuthAction
```kotlin
sealed interface AuthAction {
    data class OnPageChanged(val page: Int) : AuthAction
    data object OnLoginClick : AuthAction
    data object OnSkipClick : AuthAction
}
```

## 💡 Tips para desarrollo

1. **Testing:** Usa los Previews de Compose para ver cambios rápidamente
2. **Imágenes:** Para producción, considera cachear imágenes localmente
3. **Idiomas:** Extrae los strings a `strings.xml` para i18n
4. **Analytics:** Agrega tracking en `onAction` del ViewModel
5. **Validación:** Agrega validación de campos si necesitas formularios

## 🚀 Próximos pasos

Para implementar login real:

1. **Crear un repositorio de autenticación:**
```kotlin
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
}
```

2. **Actualizar LoginUseCase:**
```kotlin
class LoginUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        email: String, 
        password: String
    ): Result<User> {
        return authRepository.login(email, password)
            .onSuccess { user ->
                userRepository.saveUser(user)
            }
    }
}
```

3. **Agregar formulario de login en la UI**

## 📚 Referencias

- [Coil - Image Loading](https://coil-kt.github.io/coil/compose/)
- [DataStore - Persistencia](https://developer.android.com/topic/libraries/architecture/datastore)
- [Compose Pager](https://developer.android.com/jetpack/compose/layouts/pager)
- [Material Design 3](https://m3.material.io/)

