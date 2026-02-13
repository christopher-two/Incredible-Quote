package org.christophertwo.quote.feature.products.domain.repository

import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.products.domain.model.Products

/**
 * PRODUCTS REPOSITORY INTERFACE
 *
 * Define el contrato para la gestión de persistencia de Products
 * Esta interfaz pertenece al dominio y no depende de implementaciones concretas
 * Sigue el principio de inversión de dependencias (SOLID)
 */
interface ProductsRepository {

    /**
     * Flow que emite el Products actual
     * Se actualiza automáticamente cuando cambia
     */
    val productsFlow: Flow<Products>

    /**
     * Guarda el Products
     *
     * @param products Products a guardar
     */
    suspend fun saveProducts(products: Products)

    /**
     * Obtiene el Products actual
     *
     * @return Products actual o Products.empty() si no existe
     */
    suspend fun getProducts(): Products

    /**
     * Elimina el Products guardado
     */
    suspend fun clearProducts()
}