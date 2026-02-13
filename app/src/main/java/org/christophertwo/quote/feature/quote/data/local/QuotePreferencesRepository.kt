package org.christophertwo.quote.feature.quote.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import org.christophertwo.quote.feature.quote.domain.model.Quote
import org.christophertwo.quote.feature.quote.domain.repository.QuoteRepository
import java.io.IOException

/**
 * QUOTE PREFERENCES REPOSITORY IMPLEMENTATION
 *
 * Implementación concreta de QuoteRepository usando DataStore
 * Gestiona la persistencia de datos de Quote usando DataStore con serialización
 * Esta clase pertenece a la capa de datos (data layer)
 */
class QuotePreferencesRepository(private val context: Context) : QuoteRepository {

    /**
     * DataStore extension para Quote
     * Crea un archivo "quote_prefs.json" en el almacenamiento interno
     */
    private val Context.quoteDataStore: DataStore<Quote> by dataStore(
        fileName = "quote_prefs.json",
        serializer = QuoteSerializer
    )

    /**
     * Flow que emite el Quote actual
     * Se actualiza automáticamente cuando cambia
     */
    override val quoteFlow: Flow<Quote> = context.quoteDataStore.data
        .catch { exception ->
            // Manejar errores de lectura
            if (exception is IOException) {
                println("Error leyendo Quote DataStore: ${exception.message}")
                emit(Quote.empty())
            } else {
                throw exception
            }
        }

    /**
     * Guarda el Quote completo en DataStore
     *
     * @param quote Quote a guardar
     */
    override suspend fun saveQuote(quote: Quote) {
        context.quoteDataStore.updateData { quote }
    }

    /**
     * Obtiene el Quote actual
     *
     * @return Quote actual o Quote.empty() si no existe
     */
    override suspend fun getQuote(): Quote {
        return try {
            var currentQuote = Quote.empty()
            context.quoteDataStore.data.collect { quote ->
                currentQuote = quote
            }
            currentQuote
        } catch (e: Exception) {
            Quote.empty()
        }
    }

    /**
     * Elimina el Quote guardado
     */
    override suspend fun clearQuote() {
        context.quoteDataStore.updateData { Quote.empty() }
    }
}