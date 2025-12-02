package com.stoq.StockWise.product.presentation.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun ConfirmProductDialog(
    name: String?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (name != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            },
            title = { Text("Confirmar producto detectado") },
            text = { Text("¿Registrar '$name' como nuevo producto?") }
        )
    }
}
