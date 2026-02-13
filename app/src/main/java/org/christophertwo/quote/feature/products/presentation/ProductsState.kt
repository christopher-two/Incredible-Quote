package org.christophertwo.quote.feature.products.presentation

data class ProductsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    // TODO: Agregar propiedades del estado
)