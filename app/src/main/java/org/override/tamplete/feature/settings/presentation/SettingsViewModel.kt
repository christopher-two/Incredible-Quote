package org.override.tamplete.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materialkolor.PaletteStyle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.override.tamplete.feature.navigation.controllers.NavigationController
import org.override.tamplete.feature.settings.domain.model.ThemePreferences
import org.override.tamplete.feature.settings.domain.usecases.SettingsUseCases

class SettingsViewModel(
    private val settingsUseCases: SettingsUseCases,
    private val navigationController: NavigationController
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadThemePreferences()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    private fun loadThemePreferences() {
        viewModelScope.launch {
            settingsUseCases.getThemePreferences().collect { prefs ->
                _state.update { currentState ->
                    currentState.copy(
                        isDarkMode = prefs.isDarkMode,
                        useDynamicColors = prefs.useDynamicColors,
                        seedColor = with(ThemePreferences.Companion) { prefs.toColor() },
                        paletteStyle = getPaletteStyleFromString(prefs.paletteStyle),
                        contrastLevel = prefs.contrastLevel.toFloat(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnBackClick -> {
                /**
                 * Lógica de navegación inteligente para BackHandler:
                 *
                 * - Si currentScreen != MAIN: Navegación interna (volver a pantalla principal de Settings)
                 * - Si currentScreen == MAIN: Navegación externa con NavigationController (salir de Settings)
                 *
                 * Esto permite que el botón back del sistema funcione correctamente:
                 * About/Help/Privacy/Terms → MAIN → Sale de Settings
                 */
                if (_state.value.currentScreen != SettingsDestination.MAIN) {
                    _state.update { it.copy(currentScreen = SettingsDestination.MAIN) }
                } else {
                    navigationController.back()
                }
            }

            // Theme Actions
            is SettingsAction.OnDarkModeToggle -> {
                viewModelScope.launch {
                    settingsUseCases.updateDarkMode(action.enabled)
                }
            }
            is SettingsAction.OnDynamicColorsToggle -> {
                viewModelScope.launch {
                    settingsUseCases.updateDynamicColors(action.enabled)
                }
            }
            is SettingsAction.OnSeedColorChange -> {
                viewModelScope.launch {
                    settingsUseCases.updateSeedColor(action.color)
                }
            }
            is SettingsAction.OnPaletteStyleChange -> {
                viewModelScope.launch {
                    settingsUseCases.updatePaletteStyle(action.style)
                }
            }
            is SettingsAction.OnContrastLevelChange -> {
                viewModelScope.launch {
                    settingsUseCases.updateContrastLevel(action.level.toDouble())
                }
            }
            is SettingsAction.OnResetTheme -> {
                viewModelScope.launch {
                    settingsUseCases.resetThemePreferences()
                    _state.update { it.copy(snackbarMessage = "Theme reset to default") }
                }
            }
            is SettingsAction.OnColorPickerToggle -> {
                _state.update { it.copy(showColorPicker = !it.showColorPicker) }
            }

            // App Settings Actions
            is SettingsAction.OnNotificationsToggle -> {
                _state.update { it.copy(notificationsEnabled = action.enabled) }
                showSnackbar("Notifications ${if (action.enabled) "enabled" else "disabled"}")
            }
            is SettingsAction.OnAutoSyncToggle -> {
                _state.update { it.copy(autoSyncEnabled = action.enabled) }
            }
            is SettingsAction.OnDataCollectionToggle -> {
                _state.update { it.copy(dataCollectionEnabled = action.enabled) }
            }
            is SettingsAction.OnClearCache -> {
                viewModelScope.launch {
                    // Simular limpieza de cache
                    _state.update { it.copy(cacheSize = "0 MB") }
                    showSnackbar("Cache cleared successfully")
                }
            }

            // Navigation Links
            is SettingsAction.OnAboutClick -> {
                _state.update { it.copy(currentScreen = SettingsDestination.ABOUT) }
            }
            is SettingsAction.OnPrivacyPolicyClick -> {
                _state.update { it.copy(currentScreen = SettingsDestination.PRIVACY_POLICY) }
            }
            is SettingsAction.OnTermsClick -> {
                _state.update { it.copy(currentScreen = SettingsDestination.TERMS) }
            }
            is SettingsAction.OnHelpClick -> {
                _state.update { it.copy(currentScreen = SettingsDestination.HELP) }
            }

            is SettingsAction.OnSnackbarDismissed -> {
                _state.update { it.copy(snackbarMessage = null) }
            }
        }
    }

    private fun showSnackbar(message: String) {
        _state.update { it.copy(snackbarMessage = message) }
    }

    private fun getPaletteStyleFromString(name: String): PaletteStyle {
        return when (name) {
            PaletteStyle.TonalSpot.name -> PaletteStyle.TonalSpot
            PaletteStyle.Neutral.name -> PaletteStyle.Neutral
            PaletteStyle.Vibrant.name -> PaletteStyle.Vibrant
            PaletteStyle.Expressive.name -> PaletteStyle.Expressive
            PaletteStyle.Rainbow.name -> PaletteStyle.Rainbow
            PaletteStyle.FruitSalad.name -> PaletteStyle.FruitSalad
            PaletteStyle.Monochrome.name -> PaletteStyle.Monochrome
            PaletteStyle.Fidelity.name -> PaletteStyle.Fidelity
            PaletteStyle.Content.name -> PaletteStyle.Content
            else -> PaletteStyle.Expressive
        }
    }

}