package org.christophertwo.quote.feature.clients.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.clients.data.local.entity.ClientEntity

/**
 * Data Access Object para Clientes
 */
@Dao
interface ClientDao {

    /**
     * Obtener todos los clientes activos
     */
    @Query("SELECT * FROM clients WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActive(): Flow<List<ClientEntity>>

    /**
     * Obtener todos los clientes
     */
    @Query("SELECT * FROM clients ORDER BY name ASC")
    fun getAll(): Flow<List<ClientEntity>>

    /**
     * Obtener cliente por ID
     */
    @Query("SELECT * FROM clients WHERE id = :id")
    suspend fun getById(id: Int): ClientEntity?

    /**
     * Buscar clientes por nombre o email
     */
    @Query("SELECT * FROM clients WHERE (name LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%') AND isActive = 1")
    fun search(query: String): Flow<List<ClientEntity>>

    /**
     * Obtener clientes por tipo
     */
    @Query("SELECT * FROM clients WHERE type = :type AND isActive = 1 ORDER BY name ASC")
    fun getByType(type: String): Flow<List<ClientEntity>>

    /**
     * Insertar cliente
     */
    @Insert
    suspend fun insert(client: ClientEntity): Long

    /**
     * Actualizar cliente
     */
    @Update
    suspend fun update(client: ClientEntity)

    /**
     * Eliminar cliente
     */
    @Delete
    suspend fun delete(client: ClientEntity)

    /**
     * Obtener cantidad de clientes
     */
    @Query("SELECT COUNT(*) FROM clients WHERE isActive = 1")
    suspend fun getCount(): Int
}
