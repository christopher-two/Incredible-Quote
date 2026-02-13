package org.override.tamplete.main

/**
 * MAIN ACTION - Acciones que el usuario puede realizar
 *
 * En arquitectura MVI, las Actions representan las intenciones del usuario
 * Son eventos que modifican el estado de la aplicación
 */
sealed interface MainAction {

    /**
     * Acción para inicializar la aplicación
     * Se ejecuta al iniciar para cargar datos iniciales
     */
    data object InitializeApp : MainAction

    /**
     * Acción para reintentar la carga si falla
     */
    data object RetryInitialization : MainAction

    /**
     * Acción para marcar que el usuario vio el splash
     */
    data object OnSplashComplete : MainAction

    /**
     * Acción de logout de la aplicación
     */
    data object Logout : MainAction

    /**
     * Acción para limpiar mensajes de error
     */
    data object ClearError : MainAction
}

