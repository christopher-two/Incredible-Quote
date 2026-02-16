package org.christophertwo.quote.feature.quote.domain.model

/**
 * Estado de una cotización
 */
enum class QuoteStatus {
    PENDING,
    ACCEPTED,
    REJECTED;

    fun getDisplayName(): String = when (this) {
        PENDING -> "Pendiente"
        ACCEPTED -> "Aceptada"
        REJECTED -> "Rechazada"
    }
}
