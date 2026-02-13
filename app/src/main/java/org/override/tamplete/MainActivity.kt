package org.override.tamplete

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.override.tamplete.core.ui.theme.AppTheme
import org.override.tamplete.feature.navigation.wrappers.RootNavigationWrapper
import org.override.tamplete.feature.settings.domain.SplashScreenConfigurator
import org.override.tamplete.main.MainViewModel

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

            // Configurar SplashScreen con las preferencias del tema
            LaunchedEffect(state.themePreferences) {
                SplashScreenConfigurator.configure(
                    activity = this@MainActivity,
                    splashScreen = splashScreen,
                    preferences = state.themePreferences
                )
            }

            // Mantener el SplashScreen visible mientras está cargando
            splashScreen.setKeepOnScreenCondition {
                state.isLoading
            }

            // Aplicar tema con las preferencias del usuario
            AppTheme(preferences = state.themePreferences) {
                RootNavigationWrapper(
                    isLoggedIn = state.isAuthenticated
                )
            }
        }
    }
}