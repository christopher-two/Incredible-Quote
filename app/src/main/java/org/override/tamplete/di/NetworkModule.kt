package org.override.tamplete.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * MÓDULO DE KOIN PARA LA CONFIGURACIÓN DE KTOR HTTP CLIENT
 *
 * Este módulo configura el cliente HTTP Ktor con:
 * - Serialización JSON automática
 * - Logging para depuración
 * - Configuración de headers por defecto
 * - Motor OkHttp para Android
 */
val networkModule = module {

    /**
     * Configuración de JSON para serialización/deserialización
     *
     * - ignoreUnknownKeys: Ignora campos JSON que no existen en el modelo
     * - isLenient: Permite JSON menos estricto
     * - prettyPrint: Formatea el JSON para mejor lectura (desactivar en producción)
     * - encodeDefaults: Incluye valores por defecto en la serialización
     */
    single {
        Json {
            ignoreUnknownKeys = true  // Ignora campos desconocidos del servidor
            isLenient = true           // Acepta JSON no estándar
            prettyPrint = false        // false en producción para menor tamaño
            encodeDefaults = true      // Codifica valores por defecto
            coerceInputValues = true   // Convierte valores null a valores por defecto
        }
    }

    /**
     * Cliente HTTP de Ktor configurado
     *
     * single: Crea una instancia única (Singleton) del cliente
     * El cliente se reutiliza en toda la aplicación para mejor rendimiento
     */
    single {
        HttpClient(OkHttp) {

            /**
             * PLUGIN: Content Negotiation
             * Permite serialización/deserialización automática de objetos Kotlin a/desde JSON
             */
            install(ContentNegotiation) {
                json(get()) // Usa la configuración Json inyectada
            }

            /**
             * PLUGIN: Logging
             * Registra información sobre las peticiones y respuestas HTTP
             *
             * Niveles disponibles:
             * - NONE: Sin logs
             * - INFO: Información básica (método, URL, código de estado)
             * - HEADERS: Info + headers
             * - BODY: Info + headers + body (cuidado con datos sensibles)
             * - ALL: Todo lo anterior
             */
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Ktor: $message")
                        // En producción, usar un logger apropiado:
                        // Log.d("KtorClient", message)
                    }
                }
                level = LogLevel.BODY  // Cambiar a NONE en producción
            }

            /**
             * CONFIGURACIÓN POR DEFECTO
             * Se aplica a todas las peticiones del cliente
             */
            defaultRequest {
                // URL base de la API (cambiar según tu backend)
                url("https://api.example.com/")

                // Headers comunes para todas las peticiones
                header(HttpHeaders.ContentType, ContentType.Application.Json)

                // Ejemplo: Agregar token de autenticación
                // header(HttpHeaders.Authorization, "Bearer ${getToken()}")
            }

            /**
             * CONFIGURACIÓN DEL ENGINE (OkHttp)
             * Opciones específicas del motor HTTP
             */
            engine {
                // Configuración de timeouts
                config {
                    connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                    readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                    writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                }
            }
        }
    }
}

