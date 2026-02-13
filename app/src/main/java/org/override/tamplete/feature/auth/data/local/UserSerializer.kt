package org.override.tamplete.feature.auth.data.local

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.override.tamplete.feature.auth.domain.model.User
import java.io.InputStream
import java.io.OutputStream

/**
 * USER SERIALIZER - Serializador personalizado para DataStore
 *
 * Permite guardar y leer objetos User en DataStore usando kotlinx.serialization
 * DataStore utiliza este serializer para convertir User a bytes y viceversa
 */
object UserSerializer : Serializer<User> {

    /**
     * Valor por defecto cuando no hay datos guardados
     */
    override val defaultValue: User
        get() = User.empty()

    /**
     * Configuración de JSON para serialización
     */
    private val json = Json {
        ignoreUnknownKeys = true      // Ignora campos desconocidos
        encodeDefaults = true          // Incluye valores por defecto
        isLenient = true               // Permite JSON menos estricto
        coerceInputValues = true       // Convierte null a valores por defecto
        prettyPrint = false            // JSON compacto
    }

    /**
     * Lee datos del InputStream y los deserializa a User
     *
     * @param input Stream de entrada con los datos serializados
     * @return Objeto User deserializado
     */
    override suspend fun readFrom(input: InputStream): User {
        return try {
            // Leer bytes del input stream
            val bytes = input.readBytes()

            // Si no hay datos, retornar valor por defecto
            if (bytes.isEmpty()) {
                return defaultValue
            }

            // Deserializar JSON a objeto User
            json.decodeFromString(
                deserializer = User.serializer(),
                string = bytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    /**
     * Serializa el objeto User y lo escribe al OutputStream
     *
     * @param t Objeto User a serializar
     * @param output Stream de salida donde escribir los datos
     */
    override suspend fun writeTo(t: User, output: OutputStream) {
        try {
            // Serializar User a JSON
            val jsonString = json.encodeToString(
                serializer = User.serializer(),
                value = t
            )

            // Escribir bytes al output stream
            withContext(Dispatchers.IO) {
                output.write(jsonString.encodeToByteArray())
            }
        } catch (e: SerializationException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

