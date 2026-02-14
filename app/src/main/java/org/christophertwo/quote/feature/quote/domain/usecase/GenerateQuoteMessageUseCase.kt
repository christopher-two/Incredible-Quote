package org.christophertwo.quote.feature.quote.domain.usecase

import org.christophertwo.quote.feature.quote.presentation.QuoteState
import java.text.NumberFormat
import java.util.Locale

class GenerateQuoteMessageUseCase {

    @Suppress("DEPRECATION")
    operator fun invoke(state: QuoteState): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "MX"))

        return buildString {
            appendLine("📋 *COTIZACIÓN*")
            appendLine()

            // Producto
            state.selectedProduct?.let {
                appendLine("*${it.name}*")
            }

            // Cantidad y precio
            appendLine("${state.quantity} unidades")
            appendLine()
            appendLine("💰 *TOTAL: ${currencyFormat.format(state.total)}*")
            appendLine("Precio por unidad: ${currencyFormat.format(state.pricePerUnit)}")
            appendLine()

            appendLine("━━━━━━━━━━━━━━━━━━━━")
            appendLine("✨ Gracias por tu preferencia")
        }
    }

    /**
     * Genera un mensaje simplificado para WhatsApp
     */
    @Suppress("DEPRECATION")
    fun generateWhatsAppMessage(state: QuoteState): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "MX"))

        return buildString {
            append("🎯 *COTIZACIÓN*\n\n")

            state.selectedProduct?.let {
                append("*${it.name}*\n")
            }

            append("${state.quantity} unidades\n\n")
            append("💰 *${currencyFormat.format(state.total)}*\n")
            append("(${currencyFormat.format(state.pricePerUnit)} c/u)\n\n")
            append("¿Te interesa? ¡Hablemos! 💬")
        }
    }

    /**
     * Genera un mensaje para email (más formal)
     */
    @Suppress("DEPRECATION")
    fun generateEmailMessage(state: QuoteState): EmailMessage {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "MX"))

        val subject = "Cotización - ${state.selectedProduct?.name ?: "Producto"}"

        val body = buildString {
            appendLine("Estimado cliente,")
            appendLine()
            appendLine("Le presento la cotización solicitada:")
            appendLine()

            state.selectedProduct?.let {
                appendLine("Producto: ${it.name}")
            }

            appendLine("Cantidad: ${state.quantity} unidades")
            appendLine()
            appendLine("TOTAL: ${currencyFormat.format(state.total)}")
            appendLine("Precio por unidad: ${currencyFormat.format(state.pricePerUnit)}")
            appendLine()
            appendLine("Quedo a sus órdenes.")
            appendLine()
            appendLine("Saludos cordiales.")
        }

        return EmailMessage(subject = subject, body = body)
    }

    data class EmailMessage(
        val subject: String,
        val body: String
    )
}

