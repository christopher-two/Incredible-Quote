package org.christophertwo.quote.feature.clients.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.christophertwo.quote.feature.clients.data.local.dao.ClientDao
import org.christophertwo.quote.feature.clients.data.mapper.ClientMapper.toDomain
import org.christophertwo.quote.feature.clients.data.mapper.ClientMapper.toEntity
import org.christophertwo.quote.feature.clients.domain.model.Client
import org.christophertwo.quote.feature.clients.domain.model.ClientType
import org.christophertwo.quote.feature.clients.domain.repository.IClientRepository

/**
 * Implementación del repositorio de Clientes
 */
class ClientRepository(
    private val clientDao: ClientDao
) : IClientRepository {

    override fun getAllActive(): Flow<List<Client>> =
        clientDao.getAllActive().map { it.toDomain() }

    override fun getAll(): Flow<List<Client>> =
        clientDao.getAll().map { it.toDomain() }

    override suspend fun getById(id: Int): Client? =
        clientDao.getById(id)?.toDomain()

    override fun search(query: String): Flow<List<Client>> =
        clientDao.search(query).map { it.toDomain() }

    override fun getByType(type: ClientType): Flow<List<Client>> =
        clientDao.getByType(type.name).map { it.toDomain() }

    override suspend fun save(client: Client): Long =
        clientDao.insert(client.toEntity())

    override suspend fun update(client: Client) =
        clientDao.update(client.toEntity())

    override suspend fun delete(client: Client) =
        clientDao.delete(client.toEntity())

    override suspend fun getCount(): Int =
        clientDao.getCount()
}
