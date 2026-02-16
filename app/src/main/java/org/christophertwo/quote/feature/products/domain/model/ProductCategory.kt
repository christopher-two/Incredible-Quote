package org.christophertwo.quote.feature.products.domain.model

/**
 * Categoría de productos
 */
enum class ProductCategory {
    ELECTRONICS,
    CLOTHING,
    FOOD,
    HOME,
    TOOLS,
    OTHER;

    fun getDisplayName(): String = when (this) {
        ELECTRONICS -> "Electrónica"
        CLOTHING -> "Ropa"
        FOOD -> "Alimentos"
        HOME -> "Hogar"
        TOOLS -> "Herramientas"
        OTHER -> "Otros"
    }
}
