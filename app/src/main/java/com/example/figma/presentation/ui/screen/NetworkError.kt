package com.example.figma.presentation.ui.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NetworkErrorScreen(onDismissRequest: () -> Unit, confirmButton: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = "Ошибка") },
        confirmButton = {
            Button(
                onClick = { confirmButton() }) {
                Text(text = "Закрыть")
            }
        })
}