package org.override.tamplete.feature.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MobileScreenShare
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppTab(
    val label: String,
    val icon: ImageVector
) {
    Screen1(
        label = "Tab 1",
        icon = Icons.AutoMirrored.Filled.MobileScreenShare
    )
}