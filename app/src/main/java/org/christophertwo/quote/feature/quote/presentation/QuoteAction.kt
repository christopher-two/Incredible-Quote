package org.christophertwo.quote.feature.quote.presentation

sealed interface QuoteAction {
    data class OnProductSearchQueryChanged(
        val query: String
    ) : QuoteAction

    data class OnProductSelected(
        val product: QuoteState.Product
    ) : QuoteAction

    data object OnClearProductSelection : QuoteAction

    data object OnQuantityIncrement : QuoteAction
    data object OnQuantityDecrement : QuoteAction

    data class OnQuantityChanged(
        val quantity: String
    ) : QuoteAction

    data class OnQuickQuantitySelected(
        val quantity: Int
    ) : QuoteAction

    data class OnOptionSelected(
        val sectionTitle: String,
        val optionTitle: String
    ) : QuoteAction

    data class OnExtraCostTypeToggled(
        val costTypeId: String
    ) : QuoteAction

    data class OnProfitMarginChanged(
        val margin: Int
    ) : QuoteAction

    data class OnQuickProfitMarginSelected(
        val margin: Int
    ) : QuoteAction

    data object OnShareQuote : QuoteAction

    data object OnShareQuoteWhatsApp : QuoteAction

    data object OnShareQuoteEmail : QuoteAction

    data object OnSaveQuote : QuoteAction

    data class OnLoadSavedQuote(
        val quoteId: String
    ) : QuoteAction

    data class OnDeleteSavedQuote(
        val quoteId: String
    ) : QuoteAction

    data class OnQuotesSearchQueryChanged(
        val query: String
    ) : QuoteAction
}