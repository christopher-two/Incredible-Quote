package org.override.tamplete.main

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

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
 */
@Database(
    entities = [
        User::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "tamplete_database"
    }
}

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int
)