package org.override.tamplete.main

import org.override.tamplete.feature.settings.domain.model.ThemePreferences

/**
 * MAIN STATE - Estado de la pantalla principal
 *
 * En arquitectura MVI, el State representa el estado completo de la UI
 * Es inmutable y cada cambio genera un nuevo estado
 */
data class MainState(
    /**
     * Indica si la aplicación está cargando datos iniciales
     * Usado para controlar el SplashScreen
     */
    val isLoading: Boolean = true,

    /**
     * Indica si los datos iniciales se cargaron correctamente
     */
    val isInitialized: Boolean = false,

    /**
     * Mensaje de error si algo falla durante la carga inicial
     */
    val errorMessage: String? = null,

    /**
     * Indica si el usuario está autenticado
     * Útil para decidir qué pantalla mostrar después del splash
     */
    val isAuthenticated: Boolean = false,

    /**
     * Información adicional del usuario (opcional)
     */
    val userName: String? = null,

    /**
     * Preferencias del tema de la aplicación
     * Contiene configuración de colores, modo oscuro, etc.
     */
    val themePreferences: ThemePreferences = ThemePreferences.default()
)

