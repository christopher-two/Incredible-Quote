package org.override.tamplete

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.override.tamplete.di.appModules

/**
 * CLASE PRINCIPAL DE LA APLICACIÓN
 *
 * Esta clase se ejecuta antes que cualquier Activity o Service
 * Es el lugar ideal para inicializar bibliotecas y configuraciones globales
 * como Koin (Inyección de Dependencias)
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Inicializar Koin para Inyección de Dependencias
        startKoin {
            // Logger de Koin para ver logs en desarrollo
            // Level. ERROR en producción
            androidLogger(Level.ERROR)

            // Contexto de Android para Koin
            androidContext(this@MainApp)

            // Cargar todos los módulos de la aplicación
            // Incluye: databaseModule, networkModule, etc.
            modules(appModules)
        }
    }
}