package org.override.tamplete.feature.settings.data.local

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.override.tamplete.feature.settings.domain.model.ThemePreferences
import java.io.InputStream
import java.io.OutputStream

/**
 * THEME PREFERENCES SERIALIZER
 *
 * Serializer personalizado para guardar ThemePreferences en DataStore
 * Convierte entre ThemePreferences y JSON
 */
object ThemePreferencesSerializer : Serializer<ThemePreferences> {

    /**
     * Valor por defecto cuando no hay datos guardados
     */
    override val defaultValue: ThemePreferences
        get() = ThemePreferences.default()

    /**
     * Lee los datos del archivo y los deserializa
     */
    override suspend fun readFrom(input: InputStream): ThemePreferences {
        return try {
            Json.decodeFromString(
                deserializer = ThemePreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            println("Error deserializando ThemePreferences: ${e.message}")
            defaultValue
        } catch (e: Exception) {
            println("Error leyendo ThemePreferences: ${e.message}")
            defaultValue
        }
    }

    /**
     * Serializa los datos y los escribe en el archivo
     */
    override suspend fun writeTo(t: ThemePreferences, output: OutputStream) {
        withContext(Dispatchers.IO) {
            try {
                val jsonString = Json.encodeToString(
                    serializer = ThemePreferences.serializer(),
                    value = t
                )
                output.write(jsonString.encodeToByteArray())
            } catch (e: Exception) {
                println("Error escribiendo ThemePreferences: ${e.message}")
            }
        }
    }
}

