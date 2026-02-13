package org.override.tamplete.feature.auth.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject
import org.override.tamplete.feature.auth.domain.model.User

/**
 * USER REPOSITORY INTERFACE
 *
 * Define el contrato para la gestión de persistencia del usuario
 * Esta interfaz pertenece al dominio y no depende de implementaciones concretas
 * Sigue el principio de inversión de dependencias (SOLID)
 */
interface UserRepository {

    /**
     * Flow que emite el usuario actual
     * Se actualiza automáticamente cuando cambia
     */
    val userFlow: Flow<User>

    /**
     * Guarda el usuario completo
     *
     * @param user Usuario a guardar
     */
    suspend fun saveUser(user: User)

    /**
     * Obtiene el usuario actual
     *
     * @return Usuario actual o User.empty() si no hay sesión
     */
    suspend fun getUser(): User

    /**
     * Actualiza solo el token de autenticación
     *
     * @param token Nuevo token
     */
    suspend fun updateToken(token: String)

    /**
     * Actualiza el refresh token
     *
     * @param refreshToken Nuevo refresh token
     */
    suspend fun updateRefreshToken(refreshToken: String)

    /**
     * Actualiza los tokens de autenticación
     *
     * @param token Token de acceso
     * @param refreshToken Token de refresco
     */
    suspend fun updateTokens(token: String, refreshToken: String)

    /**
     * Verifica si hay un usuario con sesión activa
     *
     * @return Flow que emite true si hay sesión activa, false si no
     */
    fun isLoggedIn(): Flow<Boolean>

    /**
     * Obtiene el token actual
     *
     * @return Flow que emite el token o null si no existe
     */
    fun getToken(): Flow<String?>

    /**
     * Obtiene el refresh token actual
     *
     * @return Flow que emite el refresh token o null si no existe
     */
    fun getRefreshToken(): Flow<String?>

    /**
     * Actualiza el perfil del usuario
     *
     * @param name Nuevo nombre
     * @param photoUrl Nueva URL de foto
     * @param phoneNumber Nuevo teléfono
     */
    suspend fun updateProfile(
        name: String? = null,
        photoUrl: String? = null,
        phoneNumber: String? = null
    )

    /**
     * Actualiza los parámetros extra del usuario
     *
     * @param extraParams Nuevos parámetros extra en formato JsonObject
     */
    suspend fun updateExtraParams(extraParams: JsonObject)

    /**
     * Marca al usuario como verificado
     */
    suspend fun markAsVerified()

    /**
     * Cierra la sesión del usuario
     * Limpia todos los datos guardados
     */
    suspend fun logout()

    /**
     * Limpia completamente los datos del usuario
     */
    suspend fun clearUser()
}

