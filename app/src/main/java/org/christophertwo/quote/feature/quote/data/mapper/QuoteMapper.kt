package org.christophertwo.quote.feature.quote.data.mapper

import org.christophertwo.quote.feature.quote.data.local.entity.QuoteEntity
import org.christophertwo.quote.feature.quote.data.local.entity.QuoteItemEntity
import org.christophertwo.quote.feature.quote.data.local.entity.QuoteWithItems
import org.christophertwo.quote.feature.quote.domain.model.Quote
import org.christophertwo.quote.feature.quote.domain.model.QuoteItem
import org.christophertwo.quote.feature.quote.domain.model.QuoteWithItemsData

/**
 * Mapper para convertir entre entities de Quote y DTOs
 */
object QuoteMapper {

    fun QuoteEntity.toDomain(): Quote = Quote(
        id = id,
        clientId = clientId,
        createdAt = createdAt,
        status = status,
        totalAmount = totalAmount,
        notes = notes,
        updatedAt = updatedAt
    )

    fun Quote.toEntity(): QuoteEntity = QuoteEntity(
        id = id,
        clientId = clientId,
        createdAt = createdAt,
        status = status,
        totalAmount = totalAmount,
        notes = notes,
        updatedAt = updatedAt
    )

    fun QuoteItemEntity.toDomain(): QuoteItem = QuoteItem(
        id = id,
        quoteId = quoteId,
        productId = productId,
        quantity = quantity,
        unitPrice = unitPrice,
        subtotal = subtotal
    )

    fun QuoteItem.toEntity(): QuoteItemEntity = QuoteItemEntity(
        id = id,
        quoteId = quoteId,
        productId = productId,
        quantity = quantity,
        unitPrice = unitPrice,
        subtotal = subtotal
    )

    fun QuoteWithItems.toDomain(): QuoteWithItemsData = QuoteWithItemsData(
        quote = quote.toDomain(),
        items = items.map { it.toDomain() }
    )

    fun List<QuoteEntity>.toDomain(): List<Quote> = map { it.toDomain() }
    fun List<QuoteItem>.toEntity(): List<QuoteItemEntity> = map { it.toEntity() }
    fun List<QuoteWithItems>.toDomainWithItems(): List<QuoteWithItemsData> = map { it.toDomain() }
}
