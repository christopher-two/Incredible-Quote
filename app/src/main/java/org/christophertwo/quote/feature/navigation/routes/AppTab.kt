package org.christophertwo.quote.feature.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MobileScreenShare
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppTab(
    val label: String,
    val icon: ImageVector
) {
    Quote(
        label = "Quote",
        icon = Icons.Default.RequestQuote
    ),
    Products(
        label = "Products",
        icon = Icons.Default.ShoppingCart
    )
}