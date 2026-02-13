package org.override.tamplete.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.override.tamplete.feature.settings.data.local.ThemePreferencesRepository
import org.override.tamplete.feature.auth.domain.repository.UserRepository

/**
 * MAIN VIEW MODEL - Arquitectura MVI
 *
 * Gestiona el estado de la pantalla principal y procesa las acciones del usuario
 * Controla la carga inicial y el SplashScreen
 *
 * @param userRepository Interfaz del repositorio (no depende de implementación concreta)
 * @param themePreferencesRepository Repositorio de preferencias del tema
 */
class MainViewModel(
    private val userRepository: UserRepository,
    private val themePreferencesRepository: ThemePreferencesRepository
) : ViewModel() {

    // Estado privado mutable
    private val _state = MutableStateFlow(MainState())

    // Estado público inmutable para observar desde la UI
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        // Inicializar automáticamente la aplicación al crear el ViewModel
        onAction(MainAction.InitializeApp)

        // Observar cambios en las preferencias del tema continuamente
        observeThemeChanges()

        // Observar cambios en el estado de autenticación continuamente
        observeAuthenticationChanges()
    }

    /**
     * Observa cambios en las preferencias del tema en tiempo real
     */
    private fun observeThemeChanges() {
        viewModelScope.launch {
            themePreferencesRepository.themePreferencesFlow.collect { preferences ->
                _state.update { it.copy(themePreferences = preferences) }
            }
        }
    }

    /**
     * Observa cambios en el estado de autenticación en tiempo real
     * Esto permite detectar cuando el usuario inicia o cierra sesión
     */
    private fun observeAuthenticationChanges() {
        viewModelScope.launch {
            userRepository.isLoggedIn().collect { isLoggedIn ->
                Log.d("MainViewModel", "🔄 Estado de autenticación cambió: isLoggedIn=$isLoggedIn")
                _state.update {
                    it.copy(isAuthenticated = isLoggedIn)
                }
            }
        }
    }

    /**
     * Procesa las acciones del usuario
     *
     * @param action Acción a procesar
     */
    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.InitializeApp -> initializeApp()
            is MainAction.RetryInitialization -> initializeApp()
            is MainAction.OnSplashComplete -> onSplashComplete()
            is MainAction.Logout -> logout()
            is MainAction.ClearError -> clearError()
        }
    }

    /**
     * Inicializa la aplicación cargando datos necesarios
     * Verifica autenticación desde DataStore
     */
    private fun initializeApp() {
        viewModelScope.launch {
            try {
                // Actualizar estado a cargando
                _state.update { it.copy(isLoading = true, errorMessage = null) }

                // Cargar preferencias del tema PRIMERO (para evitar flickering)
                val themePreferences = themePreferencesRepository.themePreferencesFlow.first()
                _state.update { it.copy(themePreferences = themePreferences) }

                // Verificar autenticación desde DataStore
                val isAuthenticated = checkUserAuthentication()

                // Obtener nombre del usuario si está autenticado
                val userName = if (isAuthenticated) {
                    userRepository.userFlow.first().name
                } else {
                    null
                }

                // Cargar configuración inicial
                loadInitialConfiguration()

                // Sincronizar datos offline
                syncOfflineData()

                // Actualizar estado cuando termine la carga
                _state.update {
                    it.copy(
                        isLoading = false,
                        isInitialized = true,
                        isAuthenticated = isAuthenticated,
                        userName = userName,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {
                // Manejar errores durante la inicialización
                _state.update {
                    it.copy(
                        isLoading = false,
                        isInitialized = false,
                        errorMessage = e.message ?: "Error al inicializar la aplicación"
                    )
                }
            }
        }
    }

    /**
     * Verifica si el usuario está autenticado
     * Consulta el DataStore para verificar la sesión
     */
    private suspend fun checkUserAuthentication(): Boolean {
        return try {
            // Obtener el estado de autenticación desde DataStore
            userRepository.isLoggedIn().first()
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Carga configuración inicial de la aplicación
     */
    private suspend fun loadInitialConfiguration() {
        delay(300)
        // Aquí cargar configuraciones, preferencias, etc.
    }

    /**
     * Sincroniza datos offline con el servidor
     */
    private suspend fun syncOfflineData() {
        delay(300)
        // Aquí sincronizar datos pendientes
    }

    /**
     * Marca que el splash screen se completó
     */
    private fun onSplashComplete() {
        _state.update { it.copy(isLoading = false) }
    }

    /**
     * Cierra sesión del usuario
     */
    private fun logout() {
        viewModelScope.launch {
            try {
                // Limpiar datos de sesión en DataStore
                userRepository.logout()

                _state.update {
                    it.copy(
                        isAuthenticated = false,
                        userName = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Error al cerrar sesión: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Limpia el mensaje de error
     */
    private fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}

