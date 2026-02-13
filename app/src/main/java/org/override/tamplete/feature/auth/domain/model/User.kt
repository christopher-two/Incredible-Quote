package org.override.tamplete.feature.auth.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * USER DTO - Data Transfer Object para Usuario
 *
 * Modelo de dominio que representa un usuario en la aplicación
 * Utiliza kotlinx.serialization para ser guardado en DataStore
 *
 * @Serializable: Permite serializar/deserializar automáticamente a JSON
 */
@Serializable
data class User(
    /**
     * ID único del usuario
     */
    val id: String,

    /**
     * Nombre completo del usuario
     */
    val name: String,

    /**
     * Email del usuario
     */
    val email: String,

    /**
     * URL de la foto de perfil (opcional)
     */
    val photoUrl: String? = null,

    /**
     * Número de teléfono (opcional)
     */
    val phoneNumber: String? = null,

    /**
     * Token de autenticación
     */
    val token: String? = null,

    /**
     * Token de refresco para renovar la sesión
     */
    val refreshToken: String? = null,

    /**
     * Timestamp de creación de la cuenta
     */
    val createdAt: Long = System.currentTimeMillis(),

    /**
     * Timestamp de última actualización
     */
    val updatedAt: Long = System.currentTimeMillis(),

    /**
     * Indica si el usuario está verificado
     */
    val isVerified: Boolean = false,

    /**
     * Rol del usuario (ej: "user", "admin", "premium")
     */
    val role: String = "user",

    /**
     * Parámetros extra personalizables
     * Permite guardar cualquier dato adicional sin modificar el modelo
     *
     * Ejemplo de uso:
     * val extraParams = buildJsonObject {
     *     put("theme", "dark")
     *     put("language", "es")
     *     put("notifications", true)
     * }
     */
    val extraParams: JsonObject? = null
) {
    companion object {
        /**
         * Usuario vacío para casos donde no hay sesión
         */
        fun empty() = User(
            id = "",
            name = "",
            email = ""
        )

        /**
         * Verifica si el usuario es válido (tiene datos)
         */
        fun User.isValid(): Boolean {
            return id.isNotEmpty() && email.isNotEmpty()
        }
    }
}

