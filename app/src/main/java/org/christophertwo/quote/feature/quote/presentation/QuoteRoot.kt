package org.christophertwo.quote.feature.quote.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.christophertwo.quote.core.ui.theme.AppTheme
import org.christophertwo.quote.feature.quote.presentation.components.ExtraCostsSection
import org.christophertwo.quote.feature.quote.presentation.components.ProductSelector
import org.christophertwo.quote.feature.quote.presentation.components.ProfitMarginSelector
import org.christophertwo.quote.feature.quote.presentation.components.QuantitySelector
import org.christophertwo.quote.feature.quote.presentation.components.SectionGroup

@Composable
fun QuoteRoot(
    viewModel: QuoteViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    QuoteScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun QuoteScreen(
    state: QuoteState,
    onAction: (QuoteAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Sección de selección de producto (siempre visible)
        ProductSelector(
            selectedProduct = state.selectedProduct,
            searchQuery = state.productSearchQuery,
            searchResults = state.productSearchResults,
            isSearching = state.isSearchingProducts,
            onAction = onAction
        )

        // Las demás secciones solo aparecen cuando se ha seleccionado un producto
        if (state.selectedProduct != null) {
            // Sección de cantidad
            QuantitySelector(
                quantity = state.quantity,
                quickQuantities = state.quickQuantities,
                onAction = onAction
            )

            state.sections.forEach {
                SectionGroup(
                    section = it,
                    onAction = onAction
                )
            }

            ExtraCostsSection(
                extraCostTypes = state.extraCostTypes,
                onAction = onAction
            )

            ProfitMarginSelector(
                profitMargin = state.profitMargin,
                quickMargins = state.quickProfitMargins,
                onAction = onAction
            )
        }
    }
}

@Preview
@Composable
private fun QuoteScreenPreview() {
    AppTheme {
        QuoteScreen(
            state = QuoteState(),
            onAction = {}
        )
    }
}

