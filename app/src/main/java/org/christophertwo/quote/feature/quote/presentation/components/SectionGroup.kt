package org.christophertwo.quote.feature.quote.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.christophertwo.quote.feature.quote.presentation.QuoteAction
import org.christophertwo.quote.feature.quote.domain.model.SectionGroup as SectionGroupModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SectionGroup(
    section: SectionGroupModel,
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = section.title,
                style = MaterialTheme.typography.headlineSmall,
            )
            ButtonGroup(
                overflowIndicator = {
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = null
                    )
                },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth(),
                expandedRatio = 0.1f
            ) {
                section.options.forEach { option ->
                    toggleableItem(
                        checked = option.isSelected,
                        label = option.title,
                        weight = 3f,
                        onCheckedChange = { checked ->
                            if (checked) {
                                onAction(
                                    QuoteAction.OnOptionSelected(
                                        sectionTitle = section.title,
                                        optionTitle = option.title
                                    )
                                )
                            }
                        },
                        icon = {
                            if (option.isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
