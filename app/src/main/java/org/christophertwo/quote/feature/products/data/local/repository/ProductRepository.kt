package org.christophertwo.quote.feature.products.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.christophertwo.quote.feature.products.data.local.dao.ProductDao
import org.christophertwo.quote.feature.products.data.mapper.ProductMapper.toDomain
import org.christophertwo.quote.feature.products.data.mapper.ProductMapper.toEntity
import org.christophertwo.quote.feature.products.domain.model.Product
import org.christophertwo.quote.feature.products.domain.repository.IProductRepository

/**
 * Implementación del repositorio de Productos
 */
class ProductRepository(
    private val productDao: ProductDao
) : IProductRepository {

    override fun getAllActive(): Flow<List<Product>> =
        productDao.getAllActive().map { it.toDomain() }

    override fun getAll(): Flow<List<Product>> =
        productDao.getAll().map { it.toDomain() }

    override suspend fun getById(id: String): Product? =
        productDao.getById(id)?.toDomain()

    override fun searchByName(query: String): Flow<List<Product>> =
        productDao.searchByName(query).map { it.toDomain() }

    override suspend fun save(product: Product): Long =
        productDao.insert(product.toEntity())

    override suspend fun update(product: Product) =
        productDao.update(product.toEntity())

    override suspend fun delete(product: Product) =
        productDao.delete(product.toEntity())

    override suspend fun getCount(): Int =
        productDao.getCount()
}
