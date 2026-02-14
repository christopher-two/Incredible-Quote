package org.christophertwo.quote.feature.quote.presentation

sealed interface ShareEvent {
    data class ShareGeneral(val message: String) : ShareEvent
    data class ShareWhatsApp(val message: String) : ShareEvent
    data class ShareEmail(val subject: String, val body: String) : ShareEvent
}
