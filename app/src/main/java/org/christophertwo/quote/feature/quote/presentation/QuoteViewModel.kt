package org.christophertwo.quote.feature.quote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.quote.feature.quote.domain.usecase.SearchProductsUseCase

class QuoteViewModel(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private var hasLoadedInitialData = false
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(QuoteState())
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
            initialValue = QuoteState()
        )

    fun onAction(action: QuoteAction) {
        when (action) {
            is QuoteAction.OnProductSearchQueryChanged -> {
                _state.update { it.copy(productSearchQuery = action.query) }
                searchProducts(action.query)
            }

            is QuoteAction.OnProductSelected -> {
                _state.update { currentState ->
                    currentState.copy(
                        selectedProduct = action.product,
                        productSearchQuery = "",
                        productSearchResults = emptyList()
                    )
                }
            }

            is QuoteAction.OnClearProductSelection -> {
                _state.update { currentState ->
                    currentState.copy(
                        selectedProduct = null,
                        productSearchQuery = "",
                        productSearchResults = emptyList()
                    )
                }
            }

            is QuoteAction.OnQuantityIncrement -> {
                _state.update { currentState ->
                    currentState.copy(quantity = currentState.quantity + 1)
                }
            }

            is QuoteAction.OnQuantityDecrement -> {
                _state.update { currentState ->
                    val newQuantity = (currentState.quantity - 1).coerceAtLeast(1)
                    currentState.copy(quantity = newQuantity)
                }
            }

            is QuoteAction.OnQuantityChanged -> {
                _state.update { currentState ->
                    val newQuantity = action.quantity.toIntOrNull()?.coerceAtLeast(1) ?: currentState.quantity
                    currentState.copy(quantity = newQuantity)
                }
            }

            is QuoteAction.OnQuickQuantitySelected -> {
                _state.update { currentState ->
                    currentState.copy(quantity = action.quantity)
                }
            }

            is QuoteAction.OnOptionSelected -> {
                _state.update { currentState ->
                    currentState.copy(
                        sections = currentState.sections.map { section ->
                            if (section.title == action.sectionTitle) {
                                section.copy(
                                    options = section.options.map { option ->
                                        option.copy(
                                            isSelected = option.title == action.optionTitle
                                        )
                                    }
                                )
                            } else {
                                section
                            }
                        }
                    )
                }
            }
            is QuoteAction.OnExtraCostTypeToggled -> {
                _state.update { currentState ->
                    currentState.copy(
                        extraCostTypes = currentState.extraCostTypes.map { costType ->
                            if (costType.id == action.costTypeId) {
                                costType.copy(isSelected = !costType.isSelected)
                            } else {
                                costType
                            }
                        }
                    )
                }
            }
        }
    }

    private fun searchProducts(query: String) {
        searchJob?.cancel()

        if (query.isBlank()) {
            _state.update { it.copy(productSearchResults = emptyList()) }
            return
        }

        searchJob = viewModelScope.launch {
            _state.update { it.copy(isSearchingProducts = true) }
            delay(300) // Debounce

            try {
                val results = searchProductsUseCase(query)
                _state.update {
                    it.copy(
                        productSearchResults = results,
                        isSearchingProducts = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        productSearchResults = emptyList(),
                        isSearchingProducts = false
                    )
                }
            }
        }
    }
}