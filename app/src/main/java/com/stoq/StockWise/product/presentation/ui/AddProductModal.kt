package com.stoq.StockWise.product.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stoq.StockWise.ui.theme.StockWiseTheme

/**
 * Modal para agregar un nuevo producto
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductModal(
    isVisible: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onDismiss: () -> Unit,
    onAddProduct: (name: String, description: String?, purchasePrice: Double?, salePrice: Double?, internalNotes: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isVisible) return

    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var salePrice by remember { mutableStateOf("") }
    var internalNotes by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header del modal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Agregar Producto",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2723)
                    )
                    
                    IconButton(
                        onClick = onDismiss,
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = Color(0xFF5D4037)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Formulario
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Campo Nombre (obligatorio)
                    OutlinedTextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text("Nombre del producto *") },
                        placeholder = { Text("Ej: Laptop Dell Inspiron") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading,
                        isError = productName.isBlank() && productName.isNotEmpty(),
                        supportingText = if (productName.isBlank() && productName.isNotEmpty()) {
                            { Text("El nombre es obligatorio") }
                        } else null
                    )
                    
                    // Campo Descripción
                    OutlinedTextField(
                        value = productDescription,
                        onValueChange = { productDescription = it },
                        label = { Text("Descripción") },
                        placeholder = { Text("Descripción del producto (opcional)") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading,
                        minLines = 2,
                        maxLines = 4
                    )
                    
                    // Campos de precios
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Precio de compra
                        OutlinedTextField(
                            value = purchasePrice,
                            onValueChange = { 
                                if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                    purchasePrice = it
                                }
                            },
                            label = { Text("Precio de compra") },
                            placeholder = { Text("0.00") },
                            modifier = Modifier.weight(1f),
                            enabled = !isLoading,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            prefix = { Text("$") }
                        )
                        
                        // Precio de venta
                        OutlinedTextField(
                            value = salePrice,
                            onValueChange = { 
                                if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                    salePrice = it
                                }
                            },
                            label = { Text("Precio de venta") },
                            placeholder = { Text("0.00") },
                            modifier = Modifier.weight(1f),
                            enabled = !isLoading,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            prefix = { Text("$") }
                        )
                    }
                    
                    // Campo Notas internas
                    OutlinedTextField(
                        value = internalNotes,
                        onValueChange = { internalNotes = it },
                        label = { Text("Notas internas") },
                        placeholder = { Text("Notas adicionales (opcional)") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading,
                        minLines = 2,
                        maxLines = 4
                    )
                    
                    // Mensaje de error
                    if (errorMessage != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                        ) {
                            Text(
                                text = errorMessage,
                                color = Color(0xFFD32F2F),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Botones
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Botón Cancelar
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            enabled = !isLoading
                        ) {
                            Text("Cancelar")
                        }
                        
                        // Botón Agregar
                        Button(
                            onClick = {
                                if (productName.isNotBlank()) {
                                    val purchasePriceValue = purchasePrice.toDoubleOrNull()
                                    val salePriceValue = salePrice.toDoubleOrNull()
                                    
                                    onAddProduct(
                                        productName.trim(),
                                        productDescription.takeIf { it.isNotBlank() },
                                        purchasePriceValue,
                                        salePriceValue,
                                        internalNotes.takeIf { it.isNotBlank() }
                                    )
                                }
                            },
                            modifier = Modifier.weight(1f),
                            enabled = !isLoading && productName.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD32F2F)
                            )
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text("Agregar")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductModalPreview() {
    StockWiseTheme {
        AddProductModal(
            isVisible = true,
            isLoading = false,
            errorMessage = null,
            onDismiss = {},
            onAddProduct = { _, _, _, _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductModalLoadingPreview() {
    StockWiseTheme {
        AddProductModal(
            isVisible = true,
            isLoading = true,
            errorMessage = null,
            onDismiss = {},
            onAddProduct = { _, _, _, _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductModalErrorPreview() {
    StockWiseTheme {
        AddProductModal(
            isVisible = true,
            isLoading = false,
            errorMessage = "Error al conectar con el servidor. Intenta de nuevo.",
            onDismiss = {},
            onAddProduct = { _, _, _, _, _ -> }
        )
    }
}
