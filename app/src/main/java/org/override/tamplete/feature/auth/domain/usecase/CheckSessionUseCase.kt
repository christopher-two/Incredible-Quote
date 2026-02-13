package org.override.tamplete.feature.auth.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.override.tamplete.feature.auth.domain.repository.UserRepository

/**
 * CHECK SESSION USE CASE
 *
 * Caso de uso para verificar si hay una sesión activa
 *
 * @param userRepository Repositorio de usuario
 */
class CheckSessionUseCase(
    private val userRepository: UserRepository
) {
    /**
     * Verifica si hay una sesión activa
     *
     * @return Flow<Boolean> que emite true si hay sesión activa
     */
    operator fun invoke(): Flow<Boolean> {
        return userRepository.isLoggedIn()
    }
}

