package org.christophertwo.quote.feature.quote.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Relación One-to-Many: Quote + Items
 *
 * Permite obtener una cotización con todos sus items en una sola consulta
 */
data class QuoteWithItems(
    @Embedded
    val quote: QuoteEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "quoteId"
    )
    val items: List<QuoteItemEntity> = emptyList()
)
