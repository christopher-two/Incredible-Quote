package org.override.tamplete.feature.settings.domain.usecases

import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import kotlinx.coroutines.flow.Flow
import org.override.tamplete.feature.settings.data.local.ThemePreferencesRepository
import org.override.tamplete.feature.settings.domain.model.ThemePreferences

/**
 * Caso de uso: Obtener las preferencias del tema
 */
class GetThemePreferencesUseCase(
    private val themeRepository: ThemePreferencesRepository
) {
    operator fun invoke(): Flow<ThemePreferences> {
        return themeRepository.themePreferencesFlow
    }
}

/**
 * Caso de uso: Actualizar el modo oscuro
 */
class UpdateDarkModeUseCase(
    private val themeRepository: ThemePreferencesRepository
) {
    suspend operator fun invoke(isDark: Boolean) {
        themeRepository.updateDarkMode(isDark)
    }
}

/**
 * Caso de uso: Actualizar colores dinámicos
 */
class UpdateDynamicColorsUseCase(
    private val themeRepository: ThemePreferencesRepository
) {
    suspend operator fun invoke(useDynamic: Boolean) {
        themeRepository.updateDynamicColors(useDynamic)
    }
}

/**
 * Caso de uso: Actualizar color semilla
 */
class UpdateSeedColorUseCase(
    private val themeRepository: ThemePreferencesRepository
) {
    suspend operator fun invoke(color: Color) {
        themeRepository.updateSeedColor(color)
    }
}

/**
 * Caso de uso: Actualizar estilo de paleta
 */
class UpdatePaletteStyleUseCase(
    private val themeRepository: ThemePreferencesRepository
) {
    suspend operator fun invoke(style: PaletteStyle) {
        themeRepository.updatePaletteStyle(style)
    }
}

/**
 * Caso de uso: Actualizar nivel de contraste
 */
class UpdateContrastLevelUseCase(
    private val themeRepository: ThemePreferencesRepository
) {
    suspend operator fun invoke(level: Double) {
        themeRepository.updateContrastLevel(level)
    }
}

/**
 * Caso de uso: Resetear preferencias a valores por defecto
 */
class ResetThemePreferencesUseCase(
    private val themeRepository: ThemePreferencesRepository
) {
    suspend operator fun invoke() {
        themeRepository.resetToDefault()
    }
}

