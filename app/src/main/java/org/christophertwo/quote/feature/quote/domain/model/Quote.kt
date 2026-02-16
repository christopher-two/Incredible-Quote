package org.christophertwo.quote.feature.quote.domain.model

/**
 * Quote DTO - Data Transfer Object para presentación
 */
data class Quote(
    val id: Int = 0,
    val clientId: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val status: QuoteStatus = QuoteStatus.PENDING,
    val totalAmount: Double = 0.0,
    val notes: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * QuoteItem DTO - Item individual en una cotización
 */
data class QuoteItem(
    val id: Int = 0,
    val quoteId: Int,
    val productId: Int,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double = quantity * unitPrice
)

/**
 * QuoteWithItems DTO - Cotización con todos sus items
 */
data class QuoteWithItemsData(
    val quote: Quote,
    val items: List<QuoteItem> = emptyList()
) {
    val totalAmount: Double
        get() = items.sumOf { it.subtotal }
}
