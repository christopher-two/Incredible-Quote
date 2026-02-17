package org.christophertwo.quote.feature.clients.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.clients.domain.model.Client
import org.christophertwo.quote.feature.clients.domain.model.ClientType
import org.christophertwo.quote.feature.clients.domain.repository.IClientRepository

/**
 * Caso de uso: Obtener todos los clientes
 */
class GetAllClientsUseCase(
    private val repository: IClientRepository
) {
    operator fun invoke(): Flow<List<Client>> = repository.getAllActive()
}

/**
 * Caso de uso: Guardar cliente
 */
class SaveClientUseCase(
    private val repository: IClientRepository
) {
    suspend operator fun invoke(client: Client): Long = repository.save(client)
}

/**
 * Caso de uso: Obtener cliente por ID
 */
class GetClientByIdUseCase(
    private val repository: IClientRepository
) {
    suspend operator fun invoke(id: String): Client? = repository.getById(id)
}

/**
 * Caso de uso: Buscar clientes
 */
class SearchClientsUseCase(
    private val repository: IClientRepository
) {
    operator fun invoke(query: String): Flow<List<Client>> = repository.search(query)
}

/**
 * Caso de uso: Obtener clientes por tipo
 */
class GetClientsByTypeUseCase(
    private val repository: IClientRepository
) {
    operator fun invoke(type: ClientType): Flow<List<Client>> = repository.getByType(type)
}

/**
 * Caso de uso: Eliminar cliente
 */
class DeleteClientUseCase(
    private val repository: IClientRepository
) {
    suspend operator fun invoke(client: Client) = repository.delete(client)
}

/**
 * Caso de uso: Actualizar cliente
 */
class UpdateClientUseCase(
    private val repository: IClientRepository
) {
    suspend operator fun invoke(client: Client) = repository.update(client)
}

/**
 * Agregador de casos de uso de Clients
 */
data class ClientUseCases(
    val getAllClients: GetAllClientsUseCase,
    val saveClient: SaveClientUseCase,
    val getClientById: GetClientByIdUseCase,
    val searchClients: SearchClientsUseCase,
    val getClientsByType: GetClientsByTypeUseCase,
    val deleteClient: DeleteClientUseCase,
    val updateClient: UpdateClientUseCase
)
