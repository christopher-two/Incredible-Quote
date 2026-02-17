package org.christophertwo.quote.core.utils

import java.util.UUID

/**
 * ID Generator utility para generar identificadores únicos
 * Utiliza UUID v4 por defecto pero soporta otros formatos
 */
object IdGenerator {
    
    /**
     * Genera un ID único usando UUID v4
     * Ejemplo: "550e8400-e29b-41d4-a716-446655440000"
     */
    fun generateId(): String = UUID.randomUUID().toString()
    
    /**
     * Genera un ID único basado en timestamp (más corto que UUID)
     * Ejemplo: "1707033384123"
     */
    fun generateTimestampId(): String = System.currentTimeMillis().toString()
    
    /**
     * Genera un ID único con prefijo personalizado
     * Ejemplo: "PROD_550e8400-e29b-41d4-a716-446655440000"
     */
    fun generateIdWithPrefix(prefix: String): String = "${prefix}_${generateId()}"
    
    /**
     * Genera un ID único basado en UUID con timestamp
     * Formato: "UUID_TIMESTAMP"
     * Ejemplo: "550e8400-e29b-41d4-a716-446655440000_1707033384123"
     */
    fun generateHybridId(): String = "${generateId()}_${System.currentTimeMillis()}"
}
