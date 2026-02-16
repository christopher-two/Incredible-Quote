package org.christophertwo.quote.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.christophertwo.quote.core.data.local.converter.EnumConverters
import org.christophertwo.quote.feature.products.data.local.entity.ProductEntity
import org.christophertwo.quote.feature.products.data.local.dao.ProductDao
import org.christophertwo.quote.feature.clients.data.local.entity.ClientEntity
import org.christophertwo.quote.feature.clients.data.local.dao.ClientDao
import org.christophertwo.quote.feature.quote.data.local.entity.QuoteEntity
import org.christophertwo.quote.feature.quote.data.local.entity.QuoteItemEntity
import org.christophertwo.quote.feature.quote.data.local.dao.QuoteDao
import org.christophertwo.quote.feature.quote.data.local.dao.QuoteItemDao

/**
 * Base de datos principal de la aplicación usando Room
 *
 * Room es una biblioteca de persistencia que proporciona una capa de abstracción
 * sobre SQLite para permitir un acceso más fluido a la base de datos.
 *
 * @Database: Marca esta clase como una base de datos Room
 * - entities: Lista de entidades (tablas) que formarán parte de la BD
 * - version: Versión de la base de datos (incrementar cuando cambien las tablas)
 * - exportSchema: Si es true, exporta el esquema de la BD a un archivo JSON
 *
 * @TypeConverters: Especifica los converters para tipos complejos
 */
@Database(
    entities = [
        ProductEntity::class,
        ClientEntity::class,
        QuoteEntity::class,
        QuoteItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(EnumConverters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Obtener acceso al DAO de Productos
     */
    abstract fun productDao(): ProductDao

    /**
     * Obtener acceso al DAO de Clientes
     */
    abstract fun clientDao(): ClientDao

    /**
     * Obtener acceso al DAO de Cotizaciones
     */
    abstract fun quoteDao(): QuoteDao

    /**
     * Obtener acceso al DAO de Items de Cotización
     */
    abstract fun quoteItemDao(): QuoteItemDao

    companion object {
        const val DATABASE_NAME = "tamplete_database"
    }
}