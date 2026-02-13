package org.override.tamplete.feature.settings.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import com.materialkolor.Contrast
import com.materialkolor.PaletteStyle
import kotlinx.serialization.Serializable

/**
 * THEME PREFERENCES - Preferencias del tema de la aplicación
 *
 * Modelo que contiene todas las preferencias de personalización del tema
 * Se guarda en DataStore y se sincroniza automáticamente
 *
 * @property isDarkMode Si el tema oscuro está activado
 * @property useDynamicColors Si se usan colores dinámicos del sistema (Material You)
 * @property seedColor Color base para generar el esquema de colores (formato ARGB hex)
 * @property paletteStyle Estilo de la paleta de colores
 * @property contrastLevel Nivel de contraste (0.0 a 1.0)
 */
@Serializable
data class ThemePreferences(
    val isDarkMode: Boolean = false,
    val useDynamicColors: Boolean = true,
    val seedColor: Long = 0xFF6750A4, // Color primario Material3 por defecto (formato ARGB)
    val paletteStyle: String = PaletteStyle.Expressive.name,
    val contrastLevel: Double = Contrast.Default.value
) {
    companion object {
        /**
         * Preferencias por defecto
         */
        fun default() = ThemePreferences()

        /**
         * Convierte el Long a Color de Compose
         * Asegura que el valor esté en el rango válido de ARGB y usa el espacio de color sRGB
         */
        fun ThemePreferences.toColor(): Color {
            // Lista de valores problemáticos que causan crashes
            val invalidColors = setOf(
                0xFFFFFFFF, // Blanco puro
                0xFF000000, // Negro puro
            )

            // Si el color es inválido, usar el color por defecto
            if (seedColor in invalidColors) {
                return Color(0xFF6750A4)
            }

            // Aseguramos que el valor tenga el canal alpha en FF si no está especificado
            val argbValue = if (seedColor and 0xFF000000 == 0L) {
                seedColor or 0xFF000000
            } else {
                seedColor
            }

            return try {
                // Extraer componentes ARGB
                val alpha = ((argbValue shr 24) and 0xFF) / 255f
                val red = ((argbValue shr 16) and 0xFF) / 255f
                val green = ((argbValue shr 8) and 0xFF) / 255f
                val blue = (argbValue and 0xFF) / 255f

                // Crear color explícitamente en espacio de color sRGB
                Color(
                    red = red,
                    green = green,
                    blue = blue,
                    alpha = alpha,
                    colorSpace = ColorSpaces.Srgb
                )
            } catch (_: Exception) {
                // En caso de cualquier error, retornar el color por defecto
                Color(0xFF6750A4)
            }
        }

        /**
         * Convierte el String a PaletteStyle
         */
        fun ThemePreferences.toPaletteStyle(): PaletteStyle {
            return when (paletteStyle) {
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
}

