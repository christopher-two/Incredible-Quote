package org.christophertwo.quote.feature.products.data.mapper

import org.christophertwo.quote.feature.products.data.local.entity.ProductEntity
import org.christophertwo.quote.feature.products.domain.model.Product

/**
 * Mapper para convertir entre ProductEntity (DB) y Product (DTO)
 */
object ProductMapper {

    fun ProductEntity.toDomain(): Product = Product(
        id = id,
        name = name,
        description = description,
        price = price,
        category = category,
        isActive = isActive,
        imageUrl = imageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun Product.toEntity(): ProductEntity = ProductEntity(
        id = id,
        name = name,
        description = description,
        price = price,
        category = category,
        isActive = isActive,
        imageUrl = imageUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun List<ProductEntity>.toDomain(): List<Product> = map { it.toDomain() }
    fun List<Product>.toEntity(): List<ProductEntity> = map { it.toEntity() }
}
