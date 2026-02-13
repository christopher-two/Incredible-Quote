package org.override.tamplete.feature.settings.domain.usecases

/**
 * DATA CLASS QUE AGRUPA TODOS LOS CASOS DE USO DE SETTINGS
 *
 * Esta clase facilita la inyección de dependencias agrupando
 * todos los casos de uso relacionados con configuraciones
 */
data class SettingsUseCases(
    val getThemePreferences: GetThemePreferencesUseCase,
    val updateDarkMode: UpdateDarkModeUseCase,
    val updateDynamicColors: UpdateDynamicColorsUseCase,
    val updateSeedColor: UpdateSeedColorUseCase,
    val updatePaletteStyle: UpdatePaletteStyleUseCase,
    val updateContrastLevel: UpdateContrastLevelUseCase,
    val resetThemePreferences: ResetThemePreferencesUseCase
)

