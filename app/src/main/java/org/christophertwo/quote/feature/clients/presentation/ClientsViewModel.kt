package org.christophertwo.quote.feature.clients.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.quote.feature.clients.domain.model.Client
import org.christophertwo.quote.feature.clients.domain.model.ClientType
import org.christophertwo.quote.feature.clients.domain.usecase.ClientUseCases

/**
 * ViewModel para la gestión de Clientes
 */
class ClientsViewModel(
    private val clientUseCases: ClientUseCases
) : ViewModel() {

    private var hasLoadedInitialData = false
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(ClientsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadClients()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ClientsState()
        )

    fun onAction(action: ClientsAction) {
        when (action) {
            is ClientsAction.OnLoadClients -> loadClients()
            is ClientsAction.OnRefresh -> loadClients()
            is ClientsAction.OnSearchQuery -> searchClients(action.query)
            is ClientsAction.OnFilterByType -> filterByType(action.type)
            is ClientsAction.OnSaveClient -> saveClient(action.client)
            is ClientsAction.OnDeleteClient -> deleteClient(action.client)
            is ClientsAction.OnUpdateClient -> updateClient(action.client)
            is ClientsAction.OnDismissError -> _state.update { it.copy(errorMessage = null) }
            is ClientsAction.OnClientSelected -> _state.update { it.copy(selectedClient = action.client) }
        }
    }

    /**
     * Cargar todos los clientes activos
     */
    private fun loadClients() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                clientUseCases.getAllClients()
                    .collect { clients ->
                        _state.update { state ->
                            state.copy(
                                clients = clients,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al cargar clientes"
                    )
                }
            }
        }
    }

    /**
     * Buscar clientes por nombre o email
     */
    private fun searchClients(query: String) {
        searchJob?.cancel()

        _state.update { it.copy(searchQuery = query) }

        if (query.isBlank()) {
            _state.update { it.copy(searchResults = emptyList()) }
            return
        }

        searchJob = viewModelScope.launch {
            try {
                clientUseCases.searchClients(query)
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
     * Filtrar clientes por tipo
     */
    private fun filterByType(type: ClientType) {
        viewModelScope.launch {
            _state.update { it.copy(selectedType = type) }
            try {
                clientUseCases.getClientsByType(type)
                    .collect { clients ->
                        _state.update { state ->
                            state.copy(
                                filteredClients = clients,
                                errorMessage = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        errorMessage = e.message ?: "Error al filtrar clientes"
                    )
                }
            }
        }
    }

    /**
     * Guardar un nuevo cliente
     */
    private fun saveClient(client: Client) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                clientUseCases.saveClient(client)
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = null,
                        selectedClient = null,
                        searchQuery = ""
                    )
                }
                loadClients()
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al guardar cliente"
                    )
                }
            }
        }
    }

    /**
     * Actualizar un cliente existente
     */
    private fun updateClient(client: Client) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                clientUseCases.updateClient(client)
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = null,
                        selectedClient = null
                    )
                }
                loadClients()
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al actualizar cliente"
                    )
                }
            }
        }
    }

    /**
     * Eliminar un cliente
     */
    private fun deleteClient(client: Client) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                clientUseCases.deleteClient(client)
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = null,
                        selectedClient = null
                    )
                }
                loadClients()
            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al eliminar cliente"
                    )
                }
            }
        }
    }
}