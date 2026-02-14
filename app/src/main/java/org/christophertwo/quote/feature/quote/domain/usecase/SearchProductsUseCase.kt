package org.christophertwo.quote.feature.quote.domain.usecase

import kotlinx.coroutines.delay
import org.christophertwo.quote.feature.quote.presentation.QuoteState

class SearchProductsUseCase {

    // Mock data - después será reemplazado con Room
    private val allProducts = listOf(
        QuoteState.Product(id = "playera_cuello_redondo", name = "Playera Cuello Redondo"),
        QuoteState.Product(id = "playera_cuello_v", name = "Playera Cuello V"),
        QuoteState.Product(id = "playera_polo", name = "Playera Polo"),
        QuoteState.Product(id = "sudadera", name = "Sudadera"),
        QuoteState.Product(id = "chamarra", name = "Chamarra"),
        QuoteState.Product(id = "playera_basica", name = "Playera Básica"),
        QuoteState.Product(id = "playera_deportiva", name = "Playera Deportiva"),
        QuoteState.Product(id = "playera_manga_larga", name = "Playera Manga Larga"),
        QuoteState.Product(id = "chaleco", name = "Chaleco"),
        QuoteState.Product(id = "pants", name = "Pants"),
    )

    suspend operator fun invoke(query: String): List<QuoteState.Product> {
        // Simular delay de búsqueda
        delay(300)

        if (query.isBlank()) {
            return emptyList()
        }

        return allProducts.filter { product ->
            product.name.contains(query, ignoreCase = true)
        }.take(5) // Limitar a 5 resultados para mejor UX
    }
}

