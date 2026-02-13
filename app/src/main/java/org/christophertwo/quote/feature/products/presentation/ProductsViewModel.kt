package org.christophertwo.quote.feature.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ProductsViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProductsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // TODO: Cargar datos iniciales aquí
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
            // TODO: Manejar acciones
            else -> Unit
        }
    }
}