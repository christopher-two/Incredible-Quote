package org.override.tamplete.feature.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Diálogo selector de color con paleta predefinida
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPickerDialog(
    currentColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val predefinedColors = listOf(
        Color(0xFF6750A4), // Purple
        Color(0xFFD32F2F), // Red
        Color(0xFFE91E63), // Pink
        Color(0xFF9C27B0), // Deep Purple
        Color(0xFF673AB7), // Indigo
        Color(0xFF3F51B5), // Blue
        Color(0xFF2196F3), // Light Blue
        Color(0xFF03A9F4), // Cyan
        Color(0xFF00BCD4), // Teal
        Color(0xFF009688), // Green
        Color(0xFF4CAF50), // Light Green
        Color(0xFF8BC34A), // Lime
        Color(0xFFCDDC39), // Yellow
        Color(0xFFFFEB3B), // Amber
        Color(0xFFFFC107), // Orange
        Color(0xFFFF9800), // Deep Orange
        Color(0xFFFF5722), // Brown
        Color(0xFF795548), // Grey
        Color(0xFF9E9E9E), // Blue Grey
        Color(0xFF607D8B)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Select Seed Color")
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Choose a color to generate the app theme",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    predefinedColors.forEach { color ->
                        ColorItem(
                            color = color,
                            isSelected = color == currentColor,
                            onClick = {
                                onColorSelected(color)
                                onDismiss()
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        modifier = modifier
    )
}

@Composable
private fun ColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (isSelected) 4.dp else 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}

