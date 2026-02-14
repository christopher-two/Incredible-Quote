package org.christophertwo.quote.feature.clients.presentation

data class ClientsState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)