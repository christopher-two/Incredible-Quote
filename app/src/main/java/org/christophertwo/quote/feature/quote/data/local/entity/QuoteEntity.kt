package org.christophertwo.quote.feature.quote.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
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
    ]
)
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val clientId: Int,

    val createdAt: Long = System.currentTimeMillis(),

    val status: QuoteStatus = QuoteStatus.PENDING,

    val totalAmount: Double = 0.0,

    val notes: String = "",

    val updatedAt: Long = System.currentTimeMillis()
)
