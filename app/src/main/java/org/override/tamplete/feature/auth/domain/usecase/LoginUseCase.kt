package org.override.tamplete.feature.auth.domain.usecase

import android.util.Log
import kotlinx.coroutines.delay
import org.override.tamplete.feature.auth.domain.model.User
import org.override.tamplete.feature.auth.domain.repository.UserRepository

/**
 * LOGIN USE CASE
 *
 * Caso de uso para realizar el login del usuario
 * Simula un proceso de autenticación con delay
 * En una app real, aquí harías la llamada a tu API
 *
 * @param userRepository Repositorio de usuario para guardar la sesión
 */
class LoginUseCase(
    private val userRepository: UserRepository
) {
    /**
     * Ejecuta el login del usuario
     *
     * @param email Email del usuario
     * @param name Nombre del usuario (opcional)
     * @return Result con el usuario logeado o el error
     */
    suspend operator fun invoke(
        email: String = "user@example.com",
        name: String = "Usuario Demo"
    ): Result<User> {
        return try {
            Log.d("LoginUseCase", "🔐 Iniciando login...")

            // Simular delay de red
            delay(2000)

            // Crear usuario de ejemplo
            val user = User(
                id = "user_${System.currentTimeMillis()}",
                name = name,
                email = email,
                token = "demo_token_${System.currentTimeMillis()}",
                refreshToken = "demo_refresh_token",
                photoUrl = "https://i.pravatar.cc/300",
                isVerified = true,
                role = "user"
            )

            Log.d("LoginUseCase", "👤 Usuario creado: ${user.name} (${user.email})")

            // Guardar usuario en DataStore
            userRepository.saveUser(user)

            Log.d("LoginUseCase", "✅ Usuario guardado en DataStore")

            Result.success(user)
        } catch (e: Exception) {
            Log.e("LoginUseCase", "❌ Error en login: ${e.message}", e)
            Result.failure(e)
        }
    }
}

