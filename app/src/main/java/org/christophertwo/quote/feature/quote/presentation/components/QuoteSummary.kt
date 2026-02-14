package org.christophertwo.quote.feature.quote.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.christophertwo.quote.feature.quote.presentation.QuoteState
import java.text.NumberFormat.getCurrencyInstance
import java.util.Locale

@Composable
fun QuoteSummary(
    state: QuoteState,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onShareWhatsAppClick: () -> Unit = {},
    onShareEmailClick: () -> Unit = {}
) {
    val currencyFormat = getCurrencyInstance(Locale("es", "MX"))

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Resumen de Cotización",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Cantidad
            SummaryRow(
                label = "Cantidad",
                value = "${state.quantity} unidades",
                isHighlighted = false
            )

            // Precio base
            SummaryRow(
                label = "Precio Base (por unidad)",
                value = currencyFormat.format(state.basePrice),
                isHighlighted = false
            )

            // Precio base total
            SummaryRow(
                label = "Precio Base Total",
                value = currencyFormat.format(state.basePriceTotal),
                isHighlighted = false
            )

            // Costos extra
            if (state.totalExtraCosts > 0) {
                SummaryRow(
                    label = "Costos Extra",
                    value = currencyFormat.format(state.totalExtraCosts),
                    isHighlighted = false
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Subtotal sin ganancia
            SummaryRow(
                label = "Subtotal sin Ganancia",
                value = currencyFormat.format(state.subtotalWithoutProfit),
                isHighlighted = false
            )

            // Margen de ganancia
            SummaryRow(
                label = "Margen de Ganancia (${state.profitMargin}%)",
                value = currencyFormat.format(state.profitAmount),
                isHighlighted = false,
                valueColor = MaterialTheme.colorScheme.tertiary
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                thickness = 2.dp
            )

            // Total
            SummaryRow(
                label = "Total",
                value = currencyFormat.format(state.total),
                isHighlighted = true,
                labelStyle = MaterialTheme.typography.titleLarge,
                valueStyle = MaterialTheme.typography.titleLarge
            )

            // Precio por unidad
            SummaryRow(
                label = "Precio por Unidad",
                value = currencyFormat.format(state.pricePerUnit),
                isHighlighted = true,
                labelStyle = MaterialTheme.typography.titleMedium,
                valueStyle = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Botón de guardar cotización
            Text(
                text = "Guardar Cotización",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text("Guardar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Botones de compartir
            Text(
                text = "Compartir Cotización",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Botón de compartir general
            Button(
                onClick = onShareClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text("Compartir")
            }

            // Botones específicos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Compartir por WhatsApp
                FilledTonalButton(
                    onClick = onShareWhatsAppClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("WhatsApp")
                }

                // Compartir por Email
                OutlinedButton(
                    onClick = onShareEmailClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Email")
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier,
    labelStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyLarge,
    valueStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyLarge,
    valueColor: androidx.compose.ui.graphics.Color? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = labelStyle,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = value,
            style = valueStyle,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
            color = valueColor ?: if (isHighlighted) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            }
        )
    }
}

