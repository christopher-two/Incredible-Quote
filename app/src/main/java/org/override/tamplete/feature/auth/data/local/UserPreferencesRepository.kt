package org.override.tamplete.feature.auth.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.JsonObject
import org.override.tamplete.feature.auth.domain.model.User
import org.override.tamplete.feature.auth.domain.repository.UserRepository
import java.io.IOException

/**
 * USER PREFERENCES REPOSITORY IMPLEMENTATION
 *
 * Implementación concreta de UserRepository usando DataStore
 * Gestiona la persistencia de datos del usuario usando DataStore con serialización
 * Esta clase pertenece a la capa de datos (data layer)
 */
class UserPreferencesRepository(private val context: Context) : UserRepository {

    /**
     * DataStore extension para User
     * Crea un archivo "user_prefs.json" en el almacenamiento interno
     */
    private val Context.userDataStore: DataStore<User> by dataStore(
        fileName = "user_prefs.json",
        serializer = UserSerializer
    )

    /**
     * Flow que emite el usuario actual
     * Se actualiza automáticamente cuando cambia
     */
    override val userFlow: Flow<User> = context.userDataStore.data
        .catch { exception ->
            // Manejar errores de lectura
            if (exception is IOException) {
                Log.e("UserPrefsRepo", "❌ Error leyendo User DataStore: ${exception.message}")
                emit(User.empty())
            } else {
                throw exception
            }
        }

    /**
     * Guarda el usuario completo en DataStore
     *
     * @param user Usuario a guardar
     */
    override suspend fun saveUser(user: User) {
        Log.d("UserPrefsRepo", "💾 Guardando usuario: ${user.name} (id=${user.id}, token=${user.token?.take(10)}...)")
        context.userDataStore.updateData { user }
        Log.d("UserPrefsRepo", "✅ Usuario guardado exitosamente")
    }

    /**
     * Obtiene el usuario actual (suspend function)
     *
     * @return Usuario actual o User.empty() si no hay sesión
     */
    override suspend fun getUser(): User {
        return try {
            var currentUser = User.empty()
            userFlow.collect { user ->
                currentUser = user
            }
            currentUser
        } catch (_: Exception) {
            User.empty()
        }
    }

    /**
     * Actualiza solo el token de autenticación
     *
     * @param token Nuevo token
     */
    override suspend fun updateToken(token: String) {
        context.userDataStore.updateData { currentUser ->
            currentUser.copy(
                token = token,
                updatedAt = System.currentTimeMillis()
            )
        }
    }

    /**
     * Actualiza el refresh token
     *
     * @param refreshToken Nuevo refresh token
     */
    override suspend fun updateRefreshToken(refreshToken: String) {
        context.userDataStore.updateData { currentUser ->
            currentUser.copy(
                refreshToken = refreshToken,
                updatedAt = System.currentTimeMillis()
            )
        }
    }

    /**
     * Actualiza los tokens de autenticación
     *
     * @param token Token de acceso
     * @param refreshToken Token de refresco
     */
    override suspend fun updateTokens(token: String, refreshToken: String) {
        context.userDataStore.updateData { currentUser ->
            currentUser.copy(
                token = token,
                refreshToken = refreshToken,
                updatedAt = System.currentTimeMillis()
            )
        }
    }

    /**
     * Verifica si hay un usuario con sesión activa
     *
     * @return true si hay sesión activa, false si no
     */
    override fun isLoggedIn(): Flow<Boolean> {
        return userFlow.map { user ->
            val isLoggedIn = user.id.isNotEmpty() && user.token != null
            Log.d("UserPrefsRepo", "🔍 Verificando sesión: id=${user.id.take(10)}, token=${user.token?.take(10)}, isLoggedIn=$isLoggedIn")
            isLoggedIn
        }
    }

    /**
     * Obtiene el token actual
     */
    override fun getToken(): Flow<String?> {
        return userFlow.map { it.token }
    }

    /**
     * Obtiene el refresh token actual
     */
    override fun getRefreshToken(): Flow<String?> {
        return userFlow.map { it.refreshToken }
    }

    /**
     * Actualiza el perfil del usuario
     *
     * @param name Nuevo nombre
     * @param photoUrl Nueva URL de foto
     * @param phoneNumber Nuevo teléfono
     */
    override suspend fun updateProfile(
        name: String?,
        photoUrl: String?,
        phoneNumber: String?
    ) {
        context.userDataStore.updateData { currentUser ->
            currentUser.copy(
                name = name ?: currentUser.name,
                photoUrl = photoUrl ?: currentUser.photoUrl,
                phoneNumber = phoneNumber ?: currentUser.phoneNumber,
                updatedAt = System.currentTimeMillis()
            )
        }
    }

    /**
     * Actualiza los parámetros extra del usuario
     *
     * @param extraParams Nuevos parámetros extra
     */
    override suspend fun updateExtraParams(extraParams: JsonObject) {
        context.userDataStore.updateData { currentUser ->
            currentUser.copy(
                extraParams = extraParams,
                updatedAt = System.currentTimeMillis()
            )
        }
    }

    /**
     * Marca al usuario como verificado
     */
    override suspend fun markAsVerified() {
        context.userDataStore.updateData { currentUser ->
            currentUser.copy(
                isVerified = true,
                updatedAt = System.currentTimeMillis()
            )
        }
    }

    /**
     * Cierra la sesión del usuario
     * Limpia todos los datos guardados
     */
    override suspend fun logout() {
        context.userDataStore.updateData { User.empty() }
    }

    /**
     * Limpia completamente los datos del usuario
     * Alias de logout()
     */
    override suspend fun clearUser() {
        logout()
    }
}

