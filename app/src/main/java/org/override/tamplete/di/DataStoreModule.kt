package org.override.tamplete.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.override.tamplete.feature.settings.data.local.ThemePreferencesRepository
import org.override.tamplete.feature.auth.data.local.UserPreferencesRepository
import org.override.tamplete.feature.auth.domain.repository.UserRepository

/**
 * MÓDULO DE DATASTORE
 *
 * Configura la inyección de dependencias para repositorios de DataStore
 * Utiliza inversión de dependencias: inyecta la interfaz, no la implementación
 */
val dataStoreModule = module {

    /**
     * UserRepository - Gestión de sesión del usuario
     * single<Interfaz> { Implementación }: Inyecta la interfaz del dominio
     * Esto permite cambiar la implementación sin modificar el código que la usa
     */
    single<UserRepository> {
        UserPreferencesRepository(androidContext())
    }

    /**
     * ThemePreferencesRepository - Gestión de preferencias del tema
     * Maneja la persistencia de configuraciones de UI (tema oscuro, colores, etc.)
     */
    single<ThemePreferencesRepository> {
        ThemePreferencesRepository(androidContext())
    }

    // Agregar más repositorios de DataStore aquí:
    // single<AppPreferencesRepository> { AppPreferencesRepositoryImpl(androidContext()) }
    // single<SettingsRepository> { SettingsRepositoryImpl(androidContext()) }
}

