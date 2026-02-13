package org.override.tamplete.di

import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import org.override.tamplete.feature.navigation.controllers.NavigationController
import org.override.tamplete.feature.navigation.controllers.NavigationControllerImpl
import org.override.tamplete.feature.navigation.navigator.GlobalNavigator
import org.override.tamplete.feature.navigation.navigator.HomeNavigator

/**
 * MÓDULO DE NAVEGACIÓN
 *
 * Configura la inyección de dependencias para los navegadores
 * y controladores de navegación de la aplicación
 */
val navigationModule = module {

    /**
     * GlobalNavigator - Navegación entre pantallas principales (Auth, Home, etc.)
     * single: Crea una instancia única compartida en toda la aplicación
     */
    single<GlobalNavigator>()

    /**
     * HomeNavigator - Navegación entre tabs y pantallas dentro de Home
     * single: Crea una instancia única compartida
     */
    single<HomeNavigator>()

    /**
     * NavigationController - Interfaz principal de navegación
     * single<Interface>: Inyecta la interfaz, no la implementación
     * Esto permite cambiar la implementación sin modificar el código que la usa
     */
    single<NavigationControllerImpl>() bind NavigationController::class
}

