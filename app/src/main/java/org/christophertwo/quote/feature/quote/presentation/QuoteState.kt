package org.christophertwo.quote.feature.quote.presentation

import org.christophertwo.quote.feature.quote.domain.model.ExtraCostType
import org.christophertwo.quote.feature.products.domain.model.Product
import org.christophertwo.quote.feature.quote.domain.model.SavedQuote
import org.christophertwo.quote.feature.quote.domain.model.SectionGroup
import org.christophertwo.quote.feature.quote.domain.model.SectionOption

data class QuoteState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedProduct: Product? = null,
    val productSearchQuery: String = "",
    val productSearchResults: List<Product> = emptyList(),
    val isSearchingProducts: Boolean = false,
    val savedQuotes: List<SavedQuote> = emptyList(),
    val quotesSearchQuery: String = "",
    val filteredSavedQuotes: List<SavedQuote> = emptyList(),
    val quantity: Int = 12,
    val quickQuantities: List<Int> = listOf(5, 12, 24, 50, 100),
    val profitMargin: Int = 50,
    val quickProfitMargins: List<Int> = listOf(0, 25, 50, 75, 100),
    val basePrice: Double = 100.0, // Precio base por unidad del producto
    val showQuoteSavedMessage: Boolean = false,
    val sections: List<SectionGroup> = listOf(
        SectionGroup(
            title = "Tipo de Venta",
            options = listOf(
                SectionOption(
                    title = "Mayoreo",
                    isSelected = true,
                ),
                SectionOption(
                    title = "Menudeo",
                    isSelected = false,
                ),
            ),
        ),
        SectionGroup(
            title = "Color de Producto",
            options = listOf(
                SectionOption(
                    title = "Blanco",
                    isSelected = true,
                ),
                SectionOption(
                    title = "Colores",
                    isSelected = false,
                ),
            ),
        ),
        SectionGroup(
            title = "Talla de Producto",
            options = listOf(
                SectionOption(
                    title = "CH-EG",
                    isSelected = true,
                ),
                SectionOption(
                    title = "EG",
                    isSelected = false,
                ),
                SectionOption(
                    title = "EEEG",
                    isSelected = false,
                ),
            ),
        ),
    ),
    val extraCostTypes: List<ExtraCostType> = listOf(
        ExtraCostType(
            id = "flete",
            name = "Flete",
            isSelected = false,
            cost = 0.0
        ),
        ExtraCostType(
            id = "empaque",
            name = "Empaque",
            isSelected = false,
            cost = 0.0
        ),
        ExtraCostType(
            id = "etiquetado",
            name = "Etiquetado",
            isSelected = false,
            cost = 0.0
        ),
        ExtraCostType(
            id = "maniobras",
            name = "Maniobras",
            isSelected = false,
            cost = 0.0
        ),
        ExtraCostType(
            id = "envase",
            name = "Envase",
            isSelected = false,
            cost = 0.0
        ),
        ExtraCostType(
            id = "impresion",
            name = "Impresion",
            isSelected = false,
            cost = 0.0
        ),
        ExtraCostType(
            id = "planchado",
            name = "Planchado",
            isSelected = false,
            cost = 0.0
        ),
        ExtraCostType(
            id = "otro",
            name = "Otro",
            isSelected = false,
            cost = 0.0
        ),
    ),
) {

    // Propiedades calculadas para el resumen
    val totalExtraCosts: Double
        get() = extraCostTypes.filter { it.isSelected }.sumOf { it.cost }

    val basePriceTotal: Double
        get() = basePrice * quantity

    val subtotalWithoutProfit: Double
        get() = basePriceTotal + totalExtraCosts

    val profitAmount: Double
        get() = subtotalWithoutProfit * (profitMargin / 100.0)

    val total: Double
        get() = subtotalWithoutProfit + profitAmount

    val pricePerUnit: Double
        get() = if (quantity > 0) total / quantity else 0.0
}