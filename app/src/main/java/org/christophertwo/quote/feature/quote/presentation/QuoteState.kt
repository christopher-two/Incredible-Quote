package org.christophertwo.quote.feature.quote.presentation

data class QuoteState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    // TODO: Agregar propiedades del estado
)