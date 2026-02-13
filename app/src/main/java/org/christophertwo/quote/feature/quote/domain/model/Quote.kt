package org.christophertwo.quote.feature.quote.domain.model

import kotlinx.serialization.Serializable

/**
 * QUOTE MODEL
 *
 * Modelo de dominio que representa Quote en la aplicación
 * Utiliza kotlinx.serialization para ser guardado en DataStore
 *
 * @Serializable: Permite serializar/deserializar automáticamente a JSON
 */
@Serializable
data class Quote(
    val id: String,
    // TODO: Agregar propiedades del modelo
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        /**
         * Valor vacío por defecto
         */
        fun empty() = Quote(
            id = ""
        )

        /**
         * Verifica si el objeto es válido
         */
        fun Quote.isValid(): Boolean {
            return id.isNotEmpty()
        }
    }
}