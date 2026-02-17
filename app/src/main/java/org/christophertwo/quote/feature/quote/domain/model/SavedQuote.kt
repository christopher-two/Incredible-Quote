package org.christophertwo.quote.feature.quote.domain.model

data class SavedQuote(
    val id: String,
    val productName: String,
    val quantity: Int,
    val total: Double,
    val pricePerUnit: Double,
    val timestamp: Long
)

