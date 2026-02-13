package org.override.tamplete.feature.settings.presentation

import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle

sealed interface SettingsAction {
    // Navigation
    data object OnBackClick : SettingsAction

    // Theme Actions
    data class OnDarkModeToggle(val enabled: Boolean) : SettingsAction
    data class OnDynamicColorsToggle(val enabled: Boolean) : SettingsAction
    data class OnSeedColorChange(val color: Color) : SettingsAction
    data class OnPaletteStyleChange(val style: PaletteStyle) : SettingsAction
    data class OnContrastLevelChange(val level: Float) : SettingsAction
    data object OnResetTheme : SettingsAction
    data object OnColorPickerToggle : SettingsAction

    // App Settings Actions
    data class OnNotificationsToggle(val enabled: Boolean) : SettingsAction
    data class OnAutoSyncToggle(val enabled: Boolean) : SettingsAction
    data class OnDataCollectionToggle(val enabled: Boolean) : SettingsAction
    data object OnClearCache : SettingsAction

    // Navigation Links
    data object OnAboutClick : SettingsAction
    data object OnPrivacyPolicyClick : SettingsAction
    data object OnTermsClick : SettingsAction
    data object OnHelpClick : SettingsAction

    // Snackbar
    data object OnSnackbarDismissed : SettingsAction
}