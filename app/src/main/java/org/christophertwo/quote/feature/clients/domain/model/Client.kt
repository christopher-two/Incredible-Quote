package org.christophertwo.quote.feature.clients.domain.model

/**
 * Client DTO - Data Transfer Object para presentación
 */
data class Client(
    val id: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val state: String,
    val type: ClientType,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        fun empty() = Client(
            name = "",
            email = "",
            phone = "",
            address = "",
            city = "",
            state = "",
            type = ClientType.PERSONA
        )
    }
}
