package org.christophertwo.quote.feature.products.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.christophertwo.quote.feature.products.domain.model.ProductCategory

/**
 * Entidad de Producto para la base de datos Room
 *
 * @Entity: Indica que es una tabla en la BD
 * @PrimaryKey: Define la clave primaria
 */
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String = "",

    val name: String,

    val description: String,

    val price: Double,

    val category: ProductCategory,

    val isActive: Boolean = true,

    val imageUrl: String? = null,

    val createdAt: Long = System.currentTimeMillis(),

    val updatedAt: Long = System.currentTimeMillis()
)
