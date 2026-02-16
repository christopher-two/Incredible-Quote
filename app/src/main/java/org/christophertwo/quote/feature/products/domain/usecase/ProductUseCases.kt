package org.christophertwo.quote.feature.products.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.products.domain.model.Product
import org.christophertwo.quote.feature.products.domain.repository.IProductRepository

/**
 * Caso de uso: Obtener todos los productos activos
 */
class GetAllProductsUseCase(
    private val repository: IProductRepository
) {
    operator fun invoke(): Flow<List<Product>> = repository.getAllActive()
}

/**
 * Caso de uso: Guardar producto
 */
class SaveProductUseCase(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(product: Product): Long = repository.save(product)
}

/**
 * Caso de uso: Obtener producto por ID
 */
class GetProductByIdUseCase(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(id: Int): Product? = repository.getById(id)
}

/**
 * Caso de uso: Buscar productos
 */
class SearchProductsUseCase(
    private val repository: IProductRepository
) {
    operator fun invoke(query: String): Flow<List<Product>> = repository.searchByName(query)
}

/**
 * Caso de uso: Eliminar producto
 */
class DeleteProductUseCase(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(product: Product) = repository.delete(product)
}

/**
 * Caso de uso: Actualizar producto
 */
class UpdateProductUseCase(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(product: Product) = repository.update(product)
}

/**
 * Agregador de casos de uso de Products
 * Facilita la inyección de dependencias
 */
data class ProductUseCases(
    val getAllProducts: GetAllProductsUseCase,
    val saveProduct: SaveProductUseCase,
    val getProductById: GetProductByIdUseCase,
    val searchProducts: SearchProductsUseCase,
    val deleteProduct: DeleteProductUseCase,
    val updateProduct: UpdateProductUseCase
)
