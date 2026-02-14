package org.christophertwo.quote.feature.quote.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.christophertwo.quote.feature.quote.presentation.QuoteAction

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfitMarginSelector(
    profitMargin: Int,
    quickMargins: List<Int>,
    modifier: Modifier = Modifier,
    onAction: (QuoteAction) -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface,
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
                text = "Margen de Ganancia",
                style = MaterialTheme.typography.headlineSmall,
            )

            // Porcentaje actual
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$profitMargin%",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Slider
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Slider(
                    value = profitMargin.toFloat(),
                    onValueChange = { newValue ->
                        onAction(QuoteAction.OnProfitMarginChanged(newValue.toInt()))
                    },
                    valueRange = 0f..100f,
                    modifier = Modifier.fillMaxWidth()
                )

                // Etiquetas min/max
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "0%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "100%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Chips rápidos
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                quickMargins.forEach { quickMargin ->
                    FilterChip(
                        selected = profitMargin == quickMargin,
                        onClick = {
                            onAction(QuoteAction.OnQuickProfitMarginSelected(quickMargin))
                        },
                        label = { Text("$quickMargin%") },
                        shape = MaterialTheme.shapes.extraLarge
                    )
                }
            }
        }
    }
}

