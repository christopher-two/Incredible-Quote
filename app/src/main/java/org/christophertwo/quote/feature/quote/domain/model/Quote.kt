package org.christophertwo.quote.feature.quote.domain.model

/**
 * Quote DTO - Data Transfer Object para presentación
 */
data class Quote(
    val id: String = "",
    val clientId: String,
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
    val id: String = "",
    val quoteId: String,
    val productId: String,
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
