package org.christophertwo.quote.feature.clients.data.mapper

import org.christophertwo.quote.feature.clients.data.local.entity.ClientEntity
import org.christophertwo.quote.feature.clients.domain.model.Client

/**
 * Mapper para convertir entre ClientEntity (DB) y Client (DTO)
 */
object ClientMapper {

    fun ClientEntity.toDomain(): Client = Client(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address,
        city = city,
        state = state,
        type = type,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun Client.toEntity(): ClientEntity = ClientEntity(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address,
        city = city,
        state = state,
        type = type,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun List<ClientEntity>.toDomain(): List<Client> = map { it.toDomain() }
    fun List<Client>.toEntity(): List<ClientEntity> = map { it.toEntity() }
}
