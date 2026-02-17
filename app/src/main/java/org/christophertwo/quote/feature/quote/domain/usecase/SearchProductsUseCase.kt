package org.christophertwo.quote.feature.quote.domain.usecase

import kotlinx.coroutines.delay
import org.christophertwo.quote.feature.products.domain.model.Product
import org.christophertwo.quote.feature.products.domain.model.ProductCategory

class SearchProductsUseCase {

    // Mock data - después será reemplazado con Room
    private val allProducts = listOf(
        Product(id = "playera_cuello_redondo", name = "Playera Cuello Redondo", description = "", price = 0.0, category = ProductCategory.CLOTHING),
        Product(id = "playera_cuello_v", name = "Playera Cuello V", description = "", price = 0.0, category = ProductCategory.CLOTHING),
        Product(id = "playera_polo", name = "Playera Polo", description = "", price = 0.0, category = ProductCategory.CLOTHING),
        Product(id = "sudadera", name = "Sudadera", description = "", price = 0.0, category = ProductCategory.CLOTHING),
        Product(id = "chamarra", name = "Chamarra", description = "", price = 0.0, category = ProductCategory.CLOTHING),
        Product(id = "playera_basica", name = "Playera Básica", description = "", price = 0.0, category = ProductCategory.CLOTHING),
        Product(id = "playera_deportiva", name = "Playera Deportiva", description = "", price = 0.0, category = ProductCategory.CLOTHING),
        Product(id = "playera_manga_larga", name = "Playera Manga Larga", description = "", price = 0.0, category = ProductCategory.CLOTHING),
        Product(id = "chaleco", name = "Chaleco", description = "", price = 0.0, category = ProductCategory.CLOTHING),
        Product(id = "pants", name = "Pants", description = "", price = 0.0, category = ProductCategory.CLOTHING),
    )

    suspend operator fun invoke(query: String): List<Product> {
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

