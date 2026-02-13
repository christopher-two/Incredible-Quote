package org.christophertwo.quote.feature.products.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.christophertwo.quote.feature.products.domain.model.Products
import org.christophertwo.quote.feature.products.domain.repository.ProductsRepository
import java.io.IOException

/**
 * PRODUCTS PREFERENCES REPOSITORY IMPLEMENTATION
 *
 * Implementación concreta de ProductsRepository usando DataStore
 * Gestiona la persistencia de datos de Products usando DataStore con serialización
 * Esta clase pertenece a la capa de datos (data layer)
 */
class ProductsPreferencesRepository(private val context: Context) : ProductsRepository {

    /**
     * DataStore extension para Products
     * Crea un archivo "products_prefs.json" en el almacenamiento interno
     */
    private val Context.productsDataStore: DataStore<Products> by dataStore(
        fileName = "products_prefs.json",
        serializer = ProductsSerializer
    )

    /**
     * Flow que emite el Products actual
     * Se actualiza automáticamente cuando cambia
     */
    override val productsFlow: Flow<Products> = context.productsDataStore.data
        .catch { exception ->
            // Manejar errores de lectura
            if (exception is IOException) {
                println("Error leyendo Products DataStore: ${exception.message}")
                emit(Products.empty())
            } else {
                throw exception
            }
        }

    /**
     * Guarda el Products completo en DataStore
     *
     * @param products Products a guardar
     */
    override suspend fun saveProducts(products: Products) {
        context.productsDataStore.updateData { products }
    }

    /**
     * Obtiene el Products actual
     *
     * @return Products actual o Products.empty() si no existe
     */
    override suspend fun getProducts(): Products {
        return try {
            var currentProducts = Products.empty()
            context.productsDataStore.data.collect { products ->
                currentProducts = products
            }
            currentProducts
        } catch (e: Exception) {
            Products.empty()
        }
    }

    /**
     * Elimina el Products guardado
     */
    override suspend fun clearProducts() {
        context.productsDataStore.updateData { Products.empty() }
    }
}