package org.christophertwo.quote.feature.products.data.local

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.christophertwo.quote.feature.products.domain.model.Products
import java.io.InputStream
import java.io.OutputStream

/**
 * PRODUCTS SERIALIZER - Serializador personalizado para DataStore
 *
 * Permite guardar y leer objetos Products en DataStore usando kotlinx.serialization
 * DataStore utiliza este serializer para convertir Products a bytes y viceversa
 */
object ProductsSerializer : Serializer<Products> {

    /**
     * Valor por defecto cuando no hay datos guardados
     */
    override val defaultValue: Products
        get() = Products.empty()

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
     * Lee datos del InputStream y los deserializa a Products
     *
     * @param input Stream de entrada con los datos serializados
     * @return Objeto Products deserializado
     */
    override suspend fun readFrom(input: InputStream): Products {
        return try {
            // Leer bytes del input stream
            val bytes = input.readBytes()

            // Si no hay datos, retornar valor por defecto
            if (bytes.isEmpty()) {
                return defaultValue
            }

            // Deserializar JSON a objeto Products
            json.decodeFromString(
                deserializer = Products.serializer(),
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
     * Serializa el objeto Products y lo escribe al OutputStream
     *
     * @param t Objeto Products a serializar
     * @param output Stream de salida donde escribir los datos
     */
    override suspend fun writeTo(t: Products, output: OutputStream) {
        try {
            // Serializar Products a JSON
            val jsonString = json.encodeToString(
                serializer = Products.serializer(),
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