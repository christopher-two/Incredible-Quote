package org.override.tamplete.feature.settings.data.local

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.materialkolor.PaletteStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.override.tamplete.feature.settings.domain.model.ThemePreferences
import java.io.IOException

/**
 * THEME PREFERENCES REPOSITORY
 *
 * Repositorio que gestiona la persistencia de las preferencias del tema
 * Usa DataStore para guardar y leer las preferencias
 */
class ThemePreferencesRepository(private val context: Context) {

    /**
     * DataStore extension para ThemePreferences
     * Crea un archivo "theme_prefs.json" en el almacenamiento interno
     */
    private val Context.themeDataStore: DataStore<ThemePreferences> by dataStore(
        fileName = "theme_prefs.json",
        serializer = ThemePreferencesSerializer
    )

    /**
     * Flow que emite las preferencias del tema
     * Se actualiza automáticamente cuando cambian
     */
    val themePreferencesFlow: Flow<ThemePreferences> = context.themeDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                println("Error leyendo ThemePreferences: ${exception.message}")
                emit(ThemePreferences.default())
            } else {
                throw exception
            }
        }

    /**
     * Guarda las preferencias completas del tema
     */
    suspend fun saveThemePreferences(preferences: ThemePreferences) {
        context.themeDataStore.updateData { preferences }
    }

    /**
     * Actualiza solo el modo oscuro
     */
    suspend fun updateDarkMode(isDark: Boolean) {
        context.themeDataStore.updateData { current ->
            current.copy(isDarkMode = isDark)
        }
    }

    /**
     * Actualiza si se usan colores dinámicos
     */
    suspend fun updateDynamicColors(useDynamic: Boolean) {
        context.themeDataStore.updateData { current ->
            current.copy(useDynamicColors = useDynamic)
        }
    }

    /**
     * Actualiza el color semilla
     */
    suspend fun updateSeedColor(color: Color) {
        context.themeDataStore.updateData { current ->
            // Convertir Color a ARGB asegurando que tenga alpha completo
            val argb = color.toArgb()
            val argbLong = argb.toLong() and 0xFFFFFFFFL
            current.copy(seedColor = argbLong)
        }
    }

    /**
     * Actualiza el estilo de la paleta
     */
    suspend fun updatePaletteStyle(style: PaletteStyle) {
        context.themeDataStore.updateData { current ->
            current.copy(paletteStyle = style.name)
        }
    }

    /**
     * Actualiza el nivel de contraste
     */
    suspend fun updateContrastLevel(level: Double) {
        context.themeDataStore.updateData { current ->
            current.copy(contrastLevel = level)
        }
    }

    /**
     * Resetea las preferencias a los valores por defecto
     */
    suspend fun resetToDefault() {
        context.themeDataStore.updateData {
            ThemePreferences.default()
        }
    }
}

