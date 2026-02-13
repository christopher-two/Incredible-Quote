package org.christophertwo.quote.feature.quote.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class QuoteViewModel : ViewModel() {

    private var hasLoadedInitialData = false

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
            // TODO: Manejar acciones
            else -> Unit
        }
    }
}