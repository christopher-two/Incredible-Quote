package org.christophertwo.quote.feature.clients.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.christophertwo.quote.core.ui.theme.AppTheme

@Composable
fun ClientsRoot(
    viewModel: ClientsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ClientsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ClientsScreen(
    state: ClientsState,
    onAction: (ClientsAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        ClientsScreen(
            state = ClientsState(),
            onAction = {}
        )
    }
}