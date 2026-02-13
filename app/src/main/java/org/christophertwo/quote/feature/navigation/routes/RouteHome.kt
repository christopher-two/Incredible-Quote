package org.christophertwo.quote.feature.navigation.routes

import androidx.navigation3.runtime.NavKey

interface RouteHome : NavKey {
    object Products : RouteHome
    object Quote : RouteHome
}
