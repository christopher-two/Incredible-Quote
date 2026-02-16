package org.christophertwo.quote.feature.clients.domain.model

/**
 * Tipo de cliente
 */
enum class ClientType {
    EMPRESA,
    PERSONA;

    fun getDisplayName(): String = when (this) {
        EMPRESA -> "Empresa"
        PERSONA -> "Persona"
    }
}
