package org.christophertwo.quote.feature.clients.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.christophertwo.quote.feature.clients.domain.model.ClientType

/**
 * Entidad de Cliente para la base de datos Room
 */
@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey
    val id: String = "",

    val name: String,

    val email: String,

    val phone: String,

    val address: String,

    val city: String,

    val state: String,

    val type: ClientType,

    val isActive: Boolean = true,

    val createdAt: Long = System.currentTimeMillis(),

    val updatedAt: Long = System.currentTimeMillis()
)
