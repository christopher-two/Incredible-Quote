package org.christophertwo.quote.feature.products.domain.repository

import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.products.domain.model.Product

/**
 * Interfaz de Repositorio de Productos
 * Define el contrato que debe cumplir cualquier implementación
 */
interface IProductRepository {

    /**
     * Obtener todos los productos activos
     */
    fun getAllActive(): Flow<List<Product>>

    /**
     * Obtener todos los productos
     */
    fun getAll(): Flow<List<Product>>

    /**
     * Obtener producto por ID
     */
    suspend fun getById(id: Int): Product?

    /**
     * Buscar productos por nombre
     */
    fun searchByName(query: String): Flow<List<Product>>

    /**
     * Guardar producto (insert o update)
     */
    suspend fun save(product: Product): Long

    /**
     * Actualizar producto
     */
    suspend fun update(product: Product)

    /**
     * Eliminar producto
     */
    suspend fun delete(product: Product)

    /**
     * Obtener cantidad de productos
     */
    suspend fun getCount(): Int
}
