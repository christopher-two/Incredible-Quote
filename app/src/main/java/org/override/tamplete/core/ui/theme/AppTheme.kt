package org.override.tamplete.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import com.materialkolor.DynamicMaterialExpressiveTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicMaterialThemeState
import org.override.tamplete.feature.settings.domain.model.ThemePreferences
import org.override.tamplete.feature.settings.domain.model.ThemePreferences.Companion.toColor
import org.override.tamplete.feature.settings.domain.model.ThemePreferences.Companion.toPaletteStyle

/**
 * APP THEME - Tema principal de la aplicación
 *
 * Tema configurable que soporta:
 * - Colores dinámicos de Material You (Android 12+)
 * - Color semilla personalizable
 * - Diferentes estilos de paleta (Expressive, TonalSpot, etc.)
 * - Niveles de contraste ajustables
 * - Modo oscuro/claro
 *
 * @param isDark Si el tema debe ser oscuro (por defecto sigue el sistema)
 * @param seedColor Color base para generar el esquema de colores
 * @param style Estilo de la paleta de colores
 * @param contrastLevel Nivel de contraste (0.0 a 1.0)
 * @param useDynamicColors Si se deben usar colores dinámicos del sistema (Android 12+)
 * @param content Contenido de la aplicación
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    seedColor: Color = Color(0xFF6750A4),
    style: PaletteStyle = PaletteStyle.Expressive,
    contrastLevel: Double = Contrast.Default.value,
    useDynamicColors: Boolean = true,
    content: @Composable () -> Unit
) {
    val dynamicThemeState = rememberDynamicMaterialThemeState(
        seedColor = seedColor,
        isDark = if (useDynamicColors) isDark else isSystemInDarkTheme(),
        style = style,
        contrastLevel = contrastLevel,
        specVersion = ColorSpec.SpecVersion.SPEC_2025
    )

    DynamicMaterialExpressiveTheme(
        state = dynamicThemeState,
        motionScheme = MotionScheme.expressive(),
        animate = true,
        content = {
            Surface(
                color = colorScheme.background,
                contentColor = colorScheme.onBackground,
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        },
    )
}

/**
 * APP THEME CON PREFERENCIAS
 *
 * Versión del tema que recibe las preferencias directamente
 * y las aplica automáticamente
 *
 * @param preferences Preferencias del tema guardadas en DataStore
 * @param content Contenido de la aplicación
 */
@Composable
fun AppTheme(
    preferences: ThemePreferences,
    content: @Composable () -> Unit
) {
    AppTheme(
        isDark = preferences.isDarkMode,
        seedColor = preferences.toColor(),
        style = preferences.toPaletteStyle(),
        contrastLevel = preferences.contrastLevel,
        useDynamicColors = preferences.useDynamicColors,
        content = content
    )
}