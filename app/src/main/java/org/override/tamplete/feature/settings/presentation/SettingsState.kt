package org.override.tamplete.feature.settings.presentation

import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle

enum class SettingsDestination {
    MAIN,
    ABOUT,
    HELP,
    PRIVACY_POLICY,
    TERMS
}

data class SettingsState(
    // Navigation
    val currentScreen: SettingsDestination = SettingsDestination.MAIN,

    // Theme Settings
    val isDarkMode: Boolean = false,
    val useDynamicColors: Boolean = true,
    val seedColor: Color = Color(0xFF6750A4),
    val paletteStyle: PaletteStyle = PaletteStyle.Expressive,
    val contrastLevel: Float = 0.0f,

    // UI State
    val isLoading: Boolean = true,
    val showColorPicker: Boolean = false,
    val snackbarMessage: String? = null,

    // App Settings (extensible)
    val notificationsEnabled: Boolean = true,
    val autoSyncEnabled: Boolean = true,
    val dataCollectionEnabled: Boolean = false,
    val cacheSize: String = "0 MB"
)