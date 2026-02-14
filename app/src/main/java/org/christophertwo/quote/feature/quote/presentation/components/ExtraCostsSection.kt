package org.christophertwo.quote.feature.quote.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.christophertwo.quote.feature.quote.presentation.QuoteAction
import org.christophertwo.quote.feature.quote.presentation.QuoteState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExtraCostsSection(
    extraCostTypes: List<QuoteState.ExtraCostType>,
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
            Text(
                text = "Costos Extra",
                style = MaterialTheme.typography.headlineSmall,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 4
            ) {
                extraCostTypes.forEach { costType ->
                    FilterChip(
                        selected = costType.isSelected,
                        onClick = {
                            onAction(QuoteAction.OnExtraCostTypeToggled(costType.id))
                        },
                        label = { Text(costType.name) },
                        shape = MaterialTheme.shapes.extraLarge
                    )
                }
            }
        }
    }
}
