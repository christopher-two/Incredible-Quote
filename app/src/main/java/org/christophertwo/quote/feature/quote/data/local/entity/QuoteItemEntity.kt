package org.christophertwo.quote.feature.quote.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.christophertwo.quote.feature.products.data.local.entity.ProductEntity

/**
 * Entidad de Item de Cotización (producto en una cotización)
 */
@Entity(
    tableName = "quote_items",
    foreignKeys = [
        ForeignKey(
            entity = QuoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["quoteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["quoteId"]),
        Index(value = ["productId"])
    ]
)
data class QuoteItemEntity(
    @PrimaryKey
    val id: String = "",

    val quoteId: String,

    val productId: String,

    val quantity: Int,

    val unitPrice: Double,

    val subtotal: Double = quantity * unitPrice
)
