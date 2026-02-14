package org.christophertwo.quote.feature.quote.presentation

data class QuoteState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedProduct: Product? = null,
    val productSearchQuery: String = "",
    val productSearchResults: List<Product> = emptyList(),
    val isSearchingProducts: Boolean = false,
    val quantity: Int = 12,
    val quickQuantities: List<Int> = listOf(5, 12, 24, 50, 100),
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
    data class SectionGroup(
        val title: String,
        val options: List<SectionOption>
    )
    data class SectionOption(
        val title: String,
        val isSelected: Boolean,
    )
    data class ExtraCostType(
        val id: String,
        val name: String,
        val isSelected: Boolean,
        val cost: Double
    )
    data class Product(
        val id: String,
        val name: String
    )
}