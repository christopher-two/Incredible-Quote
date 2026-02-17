package org.christophertwo.quote.feature.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.quote.feature.products.domain.model.Product
import org.christophertwo.quote.feature.products.domain.usecase.ProductUseCases

/**
 * ViewModel para la gestión de Productos
 * Maneja el estado, acciones y comunicación con los casos de uso
 */
class ProductsViewModel(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private var hasLoadedInitialData = false
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(ProductsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadProducts()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ProductsState()
        )

    fun onAction(action: ProductsAction) {
        when (action) {
            is ProductsAction.OnLoadProducts -> loadProducts()
            is ProductsAction.OnRefresh -> loadProducts()
            is ProductsAction.OnSearchQuery -> searchProducts(action.query)
            is ProductsAction.OnSaveProduct -> saveProduct(action.product)
            is ProductsAction.OnDeleteProduct -> deleteProduct(action.product)
            is ProductsAction.OnUpdateProduct -> updateProduct(action.product)
            is ProductsAction.OnDismissError -> _state.update { it.copy(errorMessage = null) }
            is ProductsAction.OnProductSelected -> _state.update { it.copy(selectedProduct = action.product) }
        }
    }

    /**
     * Cargar todos los productos activos
     */
    private fun loadProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                productUseCases.getAllProducts()
                    .collect { products ->
                        _state.update { state ->
                            state.copy(
                                products = products,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al cargar productos"
                    )
                }
            }
        }
    }

    /**
     * Buscar productos por nombre
     */
    private fun searchProducts(query: String) {
        searchJob?.cancel()

        _state.update { it.copy(searchQuery = query) }

        if (query.isBlank()) {
            _state.update { it.copy(searchResults = emptyList()) }
            return
        }

        searchJob = viewModelScope.launch {
            try {
                productUseCases.searchProducts(query)
                    .collect { results ->
                        _state.update { state ->
                            state.copy(searchResults = results)
                        }
                    }
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        searchResults = emptyList(),
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    /**
     * Guardar un nuevo producto
     */
    private fun saveProduct(product: Product) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                productUseCases.saveProduct(product)
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = null,
                        selectedProduct = null,
                        searchQuery = ""
                    )
                }
                loadProducts()
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al guardar producto"
                    )
                }
            }
        }
    }

    /**
     * Actualizar un producto existente
     */
    private fun updateProduct(product: Product) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                productUseCases.updateProduct(product)
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = null,
                        selectedProduct = null
                    )
                }
                loadProducts()
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al actualizar producto"
                    )
                }
            }
        }
    }

    /**
     * Eliminar un producto
     */
    private fun deleteProduct(product: Product) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                productUseCases.deleteProduct(product)
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = null,
                        selectedProduct = null
                    )
                }
                loadProducts()
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al eliminar producto"
                    )
                }
            }
        }
    }
}