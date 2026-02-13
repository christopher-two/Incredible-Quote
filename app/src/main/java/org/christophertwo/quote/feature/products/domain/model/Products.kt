package org.christophertwo.quote.feature.products.domain.model

import kotlinx.serialization.Serializable

/**
 * PRODUCTS MODEL
 *
 * Modelo de dominio que representa Products en la aplicación
 * Utiliza kotlinx.serialization para ser guardado en DataStore
 *
 * @Serializable: Permite serializar/deserializar automáticamente a JSON
 */
@Serializable
data class Products(
    val id: String,
    // TODO: Agregar propiedades del modelo
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        /**
         * Valor vacío por defecto
         */
        fun empty() = Products(
            id = ""
        )

        /**
         * Verifica si el objeto es válido
         */
        fun Products.isValid(): Boolean {
            return id.isNotEmpty()
        }
    }
}