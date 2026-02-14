package org.christophertwo.quote.feature.settings.domain

import android.app.Activity
import android.os.Build
import androidx.core.splashscreen.SplashScreen
import androidx.core.view.WindowCompat
import org.christophertwo.quote.feature.settings.domain.model.ThemePreferences

/**
 * SPLASH SCREEN CONFIGURATOR
 *
 * Utilidad para configurar la SplashScreen según las preferencias del tema
 * Soporta tanto la API nativa de Android 12+ como la compatibilidad hacia atrás
 */
object SplashScreenConfigurator {

    /**
     * Configura la SplashScreen con los colores del tema
     *
     * @param activity Actividad actual
     * @param splashScreen Instancia de SplashScreen
     * @param preferences Preferencias del tema
     */
    fun configure(
        activity: Activity,
        splashScreen: SplashScreen,
        preferences: ThemePreferences,
        isDarkThemeUseDynamicColors: Boolean,
    ) {
        // Aplicar color de fondo si es Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            configureSplashScreenColors(splashScreen, preferences)
        }

        // Configurar barras del sistema
        configureSystemBars(activity, preferences, isDarkThemeUseDynamicColors)
    }

    /**
     * Configura los colores de la SplashScreen (Android 12+)
     */
    private fun configureSplashScreenColors(
        splashScreen: SplashScreen,
        preferences: ThemePreferences
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Si se usan colores dinámicos, no modificar (usa el del sistema)
            if (!preferences.useDynamicColors) {
                // Configurar el color de fondo
                splashScreen.setOnExitAnimationListener { splashScreenView ->
                    // Animación de salida personalizada si se necesita
                    splashScreenView.remove()
                }
            }
        }
    }

    /**
     * Configura las barras del sistema según el tema
     */
    private fun configureSystemBars(
        activity: Activity,
        preferences: ThemePreferences,
        isDarkThemeUseDynamicColors: Boolean
    ) {
        val window = activity.window
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // Configurar apariencia de las barras según el tema
        if (!preferences.useDynamicColors) {
            windowInsetsController.isAppearanceLightStatusBars = !isDarkThemeUseDynamicColors
            windowInsetsController.isAppearanceLightNavigationBars = !isDarkThemeUseDynamicColors
        } else {
            windowInsetsController.isAppearanceLightStatusBars = !preferences.isDarkMode
            windowInsetsController.isAppearanceLightNavigationBars = !preferences.isDarkMode
        }
    }
}