package org.christophertwo.quote.feature.products.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.christophertwo.quote.core.ui.theme.AppTheme

@Composable
fun ProductsRoot(
    viewModel: ProductsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProductsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ProductsScreen(
    state: ProductsState,
    onAction: (ProductsAction) -> Unit,
) {
    // TODO: Implementar UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Products Screen")
    }
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    AppTheme {
        ProductsScreen(
            state = ProductsState(),
            onAction = {}
        )
    }
}