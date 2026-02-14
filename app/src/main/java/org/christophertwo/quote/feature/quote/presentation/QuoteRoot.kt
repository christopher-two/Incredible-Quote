package org.christophertwo.quote.feature.quote.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.quote.core.ui.theme.AppTheme
import org.christophertwo.quote.feature.quote.presentation.components.ExtraCostsSection
import org.christophertwo.quote.feature.quote.presentation.components.ProductSelector
import org.christophertwo.quote.feature.quote.presentation.components.ProfitMarginSelector
import org.christophertwo.quote.feature.quote.presentation.components.QuantitySelector
import org.christophertwo.quote.feature.quote.presentation.components.QuoteSummary
import org.christophertwo.quote.feature.quote.presentation.components.SectionGroup

@Composable
fun QuoteRoot(
    viewModel: QuoteViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Mostrar mensaje de guardado
    LaunchedEffect(state.showQuoteSavedMessage) {
        if (state.showQuoteSavedMessage) {
            snackbarHostState.showSnackbar(
                message = "✓ Cotización guardada",
                duration = androidx.compose.material3.SnackbarDuration.Short
            )
            // Ocultar el mensaje después de mostrarlo
            viewModel.onAction(QuoteAction.OnDismissSavedMessage)
        }
    }

    // Manejar eventos de compartir
    LaunchedEffect(Unit) {
        viewModel.shareEvent.collect { event ->
            when (event) {
                is ShareEvent.ShareGeneral -> {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, event.message)
                    }
                    context.startActivity(
                        Intent.createChooser(shareIntent, "Compartir cotización")
                    )
                }

                is ShareEvent.ShareWhatsApp -> {
                    val whatsappIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        setPackage("com.whatsapp")
                        putExtra(Intent.EXTRA_TEXT, event.message)
                    }
                    try {
                        context.startActivity(whatsappIntent)
                    } catch (_: Exception) {
                        // Si WhatsApp no está instalado, usar compartir general
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, event.message)
                        }
                        context.startActivity(
                            Intent.createChooser(shareIntent, "Compartir por WhatsApp")
                        )
                    }
                }

                is ShareEvent.ShareEmail -> {
                    val emailIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "message/rfc822"
                        putExtra(Intent.EXTRA_SUBJECT, event.subject)
                        putExtra(Intent.EXTRA_TEXT, event.body)
                    }
                    try {
                        context.startActivity(
                            Intent.createChooser(emailIntent, "Enviar cotización por email")
                        )
                    } catch (_: Exception) {
                        // Si no hay app de email, usar compartir general
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, event.subject)
                            putExtra(Intent.EXTRA_TEXT, event.body)
                        }
                        context.startActivity(
                            Intent.createChooser(shareIntent, "Compartir cotización")
                        )
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        QuoteScreen(
            state = state,
            onAction = viewModel::onAction
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
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
            savedQuotes = state.savedQuotes,
            quotesSearchQuery = state.quotesSearchQuery,
            filteredSavedQuotes = state.filteredSavedQuotes,
            onAction = onAction,
            modifier = Modifier.widthIn(max = 440.dp)
        )

        // Las demás secciones solo aparecen cuando se ha seleccionado un producto
        if (state.selectedProduct != null) {
            // Sección de cantidad
            QuantitySelector(
                quantity = state.quantity,
                quickQuantities = state.quickQuantities,
                onAction = onAction,
                modifier = Modifier.widthIn(max = 440.dp)
            )

            state.sections.forEach {
                SectionGroup(
                    section = it,
                    onAction = onAction,
                    modifier = Modifier.widthIn(max = 440.dp)
                )
            }

            ExtraCostsSection(
                extraCostTypes = state.extraCostTypes,
                onAction = onAction,
                modifier = Modifier.widthIn(max = 440.dp)
            )

            ProfitMarginSelector(
                profitMargin = state.profitMargin,
                quickMargins = state.quickProfitMargins,
                onAction = onAction,
                modifier = Modifier.widthIn(max = 440.dp)
            )

            // Resumen de cotización
            QuoteSummary(
                state = state,
                onSaveClick = { onAction(QuoteAction.OnSaveQuote) },
                onShareClick = { onAction(QuoteAction.OnShareQuote) },
                onShareWhatsAppClick = { onAction(QuoteAction.OnShareQuoteWhatsApp) },
                onShareEmailClick = { onAction(QuoteAction.OnShareQuoteEmail) },
                modifier = Modifier.widthIn(max = 440.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
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

