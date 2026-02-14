package org.christophertwo.quote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.quote.core.ui.theme.AppTheme
import org.christophertwo.quote.feature.navigation.wrappers.RootNavigationWrapper
import org.christophertwo.quote.feature.settings.domain.SplashScreenConfigurator
import org.christophertwo.quote.main.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * MAIN ACTIVITY - Actividad principal con arquitectura MVI
 *
 * Implementa:
 * - Arquitectura MVI (Model-View-Intent)
 * - SplashScreen nativo que se mantiene hasta completar la carga
 * - Configuración dinámica del tema según preferencias del usuario
 * - Inyección de dependencias con Koin
 * - Tema dinámico basado en preferencias del usuario
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Instalar SplashScreen antes de super.onCreate()
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Obtener ViewModel mediante Koin
            val viewModel: MainViewModel = koinViewModel()

            // Observar el estado usando StateFlow
            val state by viewModel.state.collectAsStateWithLifecycle()

            val isDarkThemeUseDynamicColors = isSystemInDarkTheme()

            // Configurar SplashScreen con las preferencias del tema
            LaunchedEffect(state.themePreferences) {
                SplashScreenConfigurator.configure(
                    activity = this@MainActivity,
                    splashScreen = splashScreen,
                    preferences = state.themePreferences,
                    isDarkThemeUseDynamicColors = isDarkThemeUseDynamicColors
                )
            }

            // Mantener el SplashScreen visible mientras está cargando
            splashScreen.setKeepOnScreenCondition {
                state.isLoading
            }

            // Aplicar tema con las preferencias del usuario
            AppTheme(preferences = state.themePreferences) {
                RootNavigationWrapper()
            }
        }
    }
}