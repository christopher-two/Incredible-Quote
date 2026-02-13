package org.christophertwo.quote.feature.auth.presentation

sealed interface AuthAction {
    data class OnPageChanged(val page: Int) : AuthAction
    data object OnLoginClick : AuthAction
    data object OnSkipClick : AuthAction
}