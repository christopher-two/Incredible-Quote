package org.override.tamplete.di

import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.override.tamplete.main.AppDatabase

/**
 * MÓDULO DE KOIN PARA LA CONFIGURACIÓN DE ROOM
 *
 * Koin es un framework de inyección de dependencias para Kotlin
 * Este módulo configura Room Database para ser inyectado donde se necesite
 */
val databaseModule = module {

    /**
     * single: Crea una instancia única (Singleton) de la base de datos
     * La base de datos se crea una sola vez y se reutiliza en toda la aplicación
     */
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = AppDatabase.DATABASE_NAME
        )
            // Opcional: Estrategia de migración destructiva (elimina y recrea la BD)
            // Solo para desarrollo, NO usar en producción
            .fallbackToDestructiveMigration(false)

            // Opcional: Agregar callbacks para eventos de la BD
             .addCallback(object : RoomDatabase.Callback() {
                 override fun onCreate(db: SupportSQLiteDatabase) {
                     // Se ejecuta cuando se crea la BD por primera vez
                     Log.d("AppDatabase", "Base de datos creada")
                 }
             })
            .build()
    }

    /**
     * Ejemplo: Proveer DAOs desde la base de datos
     * Descomentar cuando tengas DAOs definidos
     */
    // single { get<AppDatabase>().userDao() }
    // single { get<AppDatabase>().productDao() }
}