package org.christophertwo.quote.feature.products.presentation

import org.christophertwo.quote.feature.products.domain.model.Product

/**
 * Acciones para la pantalla de Productos
 */
sealed interface ProductsAction {
    data object OnLoadProducts : ProductsAction
    data object OnRefresh : ProductsAction
    data class OnSearchQuery(val query: String) : ProductsAction
    data class OnSaveProduct(val product: Product) : ProductsAction
    data class OnUpdateProduct(val product: Product) : ProductsAction
    data class OnDeleteProduct(val product: Product) : ProductsAction
    data class OnProductSelected(val product: Product) : ProductsAction
    data object OnDismissError : ProductsAction
}