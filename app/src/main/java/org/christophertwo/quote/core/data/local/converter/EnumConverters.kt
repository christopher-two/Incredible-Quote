package org.christophertwo.quote.core.data.local.converter

import androidx.room.TypeConverter
import org.christophertwo.quote.feature.products.domain.model.ProductCategory
import org.christophertwo.quote.feature.clients.domain.model.ClientType
import org.christophertwo.quote.feature.quote.domain.model.QuoteStatus

/**
 * Converters para tipos complejos en Room
 */
object EnumConverters {

    @TypeConverter
    fun fromProductCategory(value: ProductCategory): String = value.name

    @TypeConverter
    fun toProductCategory(value: String): ProductCategory = ProductCategory.valueOf(value)

    @TypeConverter
    fun fromClientType(value: ClientType): String = value.name

    @TypeConverter
    fun toClientType(value: String): ClientType = ClientType.valueOf(value)

    @TypeConverter
    fun fromQuoteStatus(value: QuoteStatus): String = value.name

    @TypeConverter
    fun toQuoteStatus(value: String): QuoteStatus = QuoteStatus.valueOf(value)
}
