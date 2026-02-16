package org.christophertwo.quote.feature.clients.domain.repository

import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.clients.domain.model.Client
import org.christophertwo.quote.feature.clients.domain.model.ClientType

/**
 * Interfaz de Repositorio de Clientes
 */
interface IClientRepository {

    /**
     * Obtener todos los clientes activos
     */
    fun getAllActive(): Flow<List<Client>>

    /**
     * Obtener todos los clientes
     */
    fun getAll(): Flow<List<Client>>

    /**
     * Obtener cliente por ID
     */
    suspend fun getById(id: Int): Client?

    /**
     * Buscar clientes por nombre o email
     */
    fun search(query: String): Flow<List<Client>>

    /**
     * Obtener clientes por tipo
     */
    fun getByType(type: ClientType): Flow<List<Client>>

    /**
     * Guardar cliente
     */
    suspend fun save(client: Client): Long

    /**
     * Actualizar cliente
     */
    suspend fun update(client: Client)

    /**
     * Eliminar cliente
     */
    suspend fun delete(client: Client)

    /**
     * Obtener cantidad de clientes
     */
    suspend fun getCount(): Int
}
