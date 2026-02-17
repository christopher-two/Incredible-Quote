package org.christophertwo.quote.feature.products.presentation

import org.christophertwo.quote.feature.products.domain.model.Product

/**
 * Estado de la pantalla de Productos
 */
data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val searchResults: List<Product> = emptyList(),
    val searchQuery: String = "",
    val selectedProduct: Product? = null,
    val errorMessage: String? = null
)