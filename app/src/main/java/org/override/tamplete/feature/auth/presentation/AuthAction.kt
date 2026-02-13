package org.override.tamplete.feature.auth.presentation

sealed interface AuthAction {
    data class OnPageChanged(val page: Int) : AuthAction
    data object OnLoginClick : AuthAction
    data object OnSkipClick : AuthAction
}