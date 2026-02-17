package org.christophertwo.quote.feature.clients.presentation

import org.christophertwo.quote.feature.clients.domain.model.Client
import org.christophertwo.quote.feature.clients.domain.model.ClientType

/**
 * Acciones para la pantalla de Clientes
 */
sealed interface ClientsAction {
    data object OnLoadClients : ClientsAction
    data object OnRefresh : ClientsAction
    data class OnSearchQuery(val query: String) : ClientsAction
    data class OnFilterByType(val type: ClientType) : ClientsAction
    data class OnSaveClient(val client: Client) : ClientsAction
    data class OnUpdateClient(val client: Client) : ClientsAction
    data class OnDeleteClient(val client: Client) : ClientsAction
    data class OnClientSelected(val client: Client) : ClientsAction
    data object OnDismissError : ClientsAction
}