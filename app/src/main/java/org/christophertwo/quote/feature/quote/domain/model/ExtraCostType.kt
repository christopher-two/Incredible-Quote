package org.christophertwo.quote.feature.quote.domain.model

data class ExtraCostType(
    val id: String,
    val name: String,
    val isSelected: Boolean,
    val cost: Double
)

