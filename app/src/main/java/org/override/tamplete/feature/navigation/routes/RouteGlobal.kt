package org.override.tamplete.feature.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface RouteGlobal : NavKey {
    @Serializable
    object Auth : RouteGlobal

    @Serializable
    object Home : RouteGlobal
    @Serializable
    object Settings : RouteGlobal
}