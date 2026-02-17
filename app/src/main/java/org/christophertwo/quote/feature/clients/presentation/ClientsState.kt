package org.christophertwo.quote.feature.clients.presentation

import org.christophertwo.quote.feature.clients.domain.model.Client
import org.christophertwo.quote.feature.clients.domain.model.ClientType

/**
 * Estado de la pantalla de Clientes
 */
data class ClientsState(
    val isLoading: Boolean = false,
    val clients: List<Client> = emptyList(),
    val searchResults: List<Client> = emptyList(),
    val filteredClients: List<Client> = emptyList(),
    val searchQuery: String = "",
    val selectedClient: Client? = null,
    val selectedType: ClientType? = null,
    val errorMessage: String? = null
)