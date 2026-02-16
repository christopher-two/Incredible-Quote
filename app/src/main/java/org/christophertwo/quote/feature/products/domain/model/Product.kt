package org.christophertwo.quote.feature.products.domain.model

/**
 * Product DTO - Data Transfer Object para presentación
 *
 * Este es el modelo de dominio que se utiliza en las capas superiores
 * Separado de ProductEntity para mayor flexibilidad
 */
data class Product(
    val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val category: ProductCategory,
    val isActive: Boolean = true,
    val imageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun empty() = Product(
            name = "",
            description = "",
            price = 0.0,
            category = ProductCategory.OTHER
        )
    }
}
