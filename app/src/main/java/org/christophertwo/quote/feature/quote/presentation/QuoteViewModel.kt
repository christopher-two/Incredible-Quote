package org.christophertwo.quote.feature.quote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.quote.feature.quote.domain.model.Quote
import org.christophertwo.quote.feature.quote.domain.model.QuoteItem
import org.christophertwo.quote.feature.quote.domain.model.QuoteStatus
import org.christophertwo.quote.feature.quote.domain.model.SavedQuote
import org.christophertwo.quote.feature.quote.domain.usecase.GenerateQuoteMessageUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.QuoteUseCases
import org.christophertwo.quote.feature.quote.domain.usecase.SearchProductsUseCase

class QuoteViewModel(
    private val quoteUseCases: QuoteUseCases,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val generateQuoteMessageUseCase: GenerateQuoteMessageUseCase
) : ViewModel() {

    private var hasLoadedInitialData = false
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(QuoteState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadAllQuotes()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = QuoteState()
        )

    private val _shareEvent = MutableSharedFlow<ShareEvent>()
    val shareEvent = _shareEvent.asSharedFlow()

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

            is QuoteAction.OnProfitMarginChanged -> {
                _state.update { currentState ->
                    currentState.copy(profitMargin = action.margin.coerceIn(0, 100))
                }
            }

            is QuoteAction.OnQuickProfitMarginSelected -> {
                _state.update { currentState ->
                    currentState.copy(profitMargin = action.margin)
                }
            }

            is QuoteAction.OnShareQuote -> {
                viewModelScope.launch {
                    val message = generateQuoteMessageUseCase(_state.value)
                    _shareEvent.emit(ShareEvent.ShareGeneral(message))
                }
            }

            is QuoteAction.OnShareQuoteWhatsApp -> {
                viewModelScope.launch {
                    val message = generateQuoteMessageUseCase.generateWhatsAppMessage(_state.value)
                    _shareEvent.emit(ShareEvent.ShareWhatsApp(message))
                }
            }

            is QuoteAction.OnShareQuoteEmail -> {
                viewModelScope.launch {
                    val emailMessage = generateQuoteMessageUseCase.generateEmailMessage(_state.value)
                    _shareEvent.emit(
                        ShareEvent.ShareEmail(
                            subject = emailMessage.subject,
                            body = emailMessage.body
                        )
                    )
                }
            }

            is QuoteAction.OnSaveQuote -> {
                saveQuoteToDatabase()
            }

            is QuoteAction.OnLoadSavedQuote -> {
                loadSavedQuote(action.quoteId)
            }

            is QuoteAction.OnDeleteSavedQuote -> {
                deleteSavedQuote(action.quoteId)
            }

            is QuoteAction.OnQuotesSearchQueryChanged -> {
                _state.update { it.copy(quotesSearchQuery = action.query) }
                filterSavedQuotes(action.query)
            }

            is QuoteAction.OnDismissSavedMessage -> {
                _state.update { it.copy(showQuoteSavedMessage = false) }
            }
        }
    }

    /**
     * Cargar todas las cotizaciones
     */
    private fun loadAllQuotes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                quoteUseCases.getAllQuotes()
                    .collect { quotesWithItems ->
                        _state.update { state ->
                            state.copy(
                                savedQuotes = quotesWithItems.map { quoteWithItems ->
                                    SavedQuote(
                                        id = quoteWithItems.quote.id,
                                        productName = "Cotización #${quoteWithItems.quote.id}",
                                        quantity = quoteWithItems.items.sumOf { it.quantity },
                                        total = quoteWithItems.totalAmount,
                                        pricePerUnit = 0.0,
                                        timestamp = quoteWithItems.quote.createdAt
                                    )
                                },
                                isLoading = false
                            )
                        }
                    }
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al cargar cotizaciones"
                    )
                }
            }
        }
    }

    /**
     * Guardar cotización en base de datos
     */
    private fun saveQuoteToDatabase() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val currentState = _state.value
                val selectedProduct = currentState.selectedProduct
                
                if (selectedProduct == null) {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = "Selecciona un producto primero"
                        )
                    }
                    return@launch
                }
                
                val newQuote = Quote(
                    clientId = "",
                    status = QuoteStatus.PENDING,
                    totalAmount = currentState.basePrice * currentState.quantity,
                    notes = ""
                )
                
                val quoteId = quoteUseCases.createQuote(newQuote)
                
                // Agregar item a la cotización
                val item = QuoteItem(
                   quoteId = quoteId.toString(),
                    productId = selectedProduct.id,
                    quantity = currentState.quantity,
                    unitPrice = selectedProduct.price
                )
                quoteUseCases.addItemToQuote(item)
                
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        showQuoteSavedMessage = true,
                        errorMessage = null
                    )
                }
                loadAllQuotes()
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al guardar cotización"
                    )
                }
            }
        }
    }

    private fun loadSavedQuote(quoteId: String) {
        viewModelScope.launch {
            try {
                val quoteWithItems = quoteUseCases.getQuoteWithItems(quoteId)
                quoteWithItems?.let { quote ->
                    // TODO: Cargar el estado de la cotización
                }
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(errorMessage = e.message)
                }
            }
        }
    }

    private fun deleteSavedQuote(quoteId: String) {
        viewModelScope.launch {
            try {
                val quoteWithItems = quoteUseCases.getQuoteWithItems(quoteId)
                quoteWithItems?.let { quote ->
                    quoteUseCases.deleteQuote(quote.quote)
                    loadAllQuotes()
                }
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(errorMessage = e.message)
                }
            }
        }
    }

    private fun filterSavedQuotes(query: String) {
        _state.update { state ->
            val filtered = if (query.isBlank()) {
                state.savedQuotes
            } else {
                state.savedQuotes.filter { quote ->
                    quote.productName.contains(query, ignoreCase = true)
                }
            }
            state.copy(filteredSavedQuotes = filtered)
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

            try {
                val results = searchProductsUseCase(query)
                _state.update {
                    it.copy(
                        productSearchResults = results,
                        isSearchingProducts = false
                    )
                }
            } catch (_: Exception) {
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