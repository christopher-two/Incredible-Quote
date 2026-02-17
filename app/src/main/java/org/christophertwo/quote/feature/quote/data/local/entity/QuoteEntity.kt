package org.christophertwo.quote.feature.quote.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.christophertwo.quote.feature.clients.data.local.entity.ClientEntity
import org.christophertwo.quote.feature.quote.domain.model.QuoteStatus

/**
 * Entidad de Cotización para la base de datos Room
 */
@Entity(
    tableName = "quotes",
    foreignKeys = [
        ForeignKey(
            entity = ClientEntity::class,
            parentColumns = ["id"],
            childColumns = ["clientId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["clientId"])
    ]
)
data class QuoteEntity(
    @PrimaryKey
    val id: String = "",

    val clientId: String,

    val createdAt: Long = System.currentTimeMillis(),

    val status: QuoteStatus = QuoteStatus.PENDING,

    val totalAmount: Double = 0.0,

    val notes: String = "",

    val updatedAt: Long = System.currentTimeMillis()
)
