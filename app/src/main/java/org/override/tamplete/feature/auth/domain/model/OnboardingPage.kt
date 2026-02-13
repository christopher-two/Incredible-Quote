package org.override.tamplete.feature.auth.domain.model

/**
 * ONBOARDING PAGE MODEL
 *
 * Modelo de dominio que representa una página del onboarding
 * Diseñado para ser reutilizable y fácilmente configurable
 *
 * @param imageUrl URL de la imagen a mostrar (puede ser local o remota)
 * @param title Título de la página
 * @param description Descripción de la página
 */
data class OnboardingPage(
    val imageUrl: String,
    val title: String,
    val description: String
)

/**
 * Datos por defecto del onboarding
 * Pueden ser fácilmente modificados o reemplazados
 */
object OnboardingDefaults {
    fun getPages(): List<OnboardingPage> {
        return listOf(
            OnboardingPage(
                imageUrl = "https://images.unsplash.com/photo-1551650975-87deedd944c3?w=800&q=80",
                title = "Bienvenido",
                description = "Comienza tu viaje con nosotros de manera fácil y rápida"
            ),
            OnboardingPage(
                imageUrl = "https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?w=800&q=80",
                title = "Organiza tu tiempo",
                description = "Gestiona tus tareas de forma eficiente y productiva"
            ),
            OnboardingPage(
                imageUrl = "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?w=800&q=80",
                title = "Trabaja en equipo",
                description = "Colabora con tu equipo y alcanza tus objetivos juntos"
            )
        )
    }
}

