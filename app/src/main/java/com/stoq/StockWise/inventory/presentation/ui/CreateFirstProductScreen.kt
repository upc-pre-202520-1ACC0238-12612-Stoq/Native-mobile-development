package com.stoq.StockWise.inventory.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stoq.StockWise.ui.theme.StockWiseTheme

/**
 * Pantalla para crear el primer producto del usuario
 * 
 * Permite al usuario agregar su primer producto al inventario
 * o saltarse este paso si no desea hacerlo ahora.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFirstProductScreen(
    onProductCreated: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productStock by remember { mutableStateOf("") }
    var isCreating by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Agrega tu Primer Producto",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Comienza agregando tu primer producto al inventario o salta este paso",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Formulario
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Campo de nombre del producto
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Nombre del producto") },
                    placeholder = { Text("Mi Producto") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                
                // Campo de precio
                OutlinedTextField(
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    label = { Text("Precio") },
                    placeholder = { Text("0.00") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                
                // Campo de stock
                OutlinedTextField(
                    value = productStock,
                    onValueChange = { productStock = it },
                    label = { Text("Stock inicial") },
                    placeholder = { Text("0") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Botón de saltar
                    OutlinedButton(
                        onClick = onSkip,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Saltar por ahora")
                    }
                    
                    // Botón de crear
                    Button(
                        onClick = {
                            if (productName.isNotBlank()) {
                                isCreating = true
                                // Simular creación del producto
                                onProductCreated()
                            }
                        },
                        enabled = productName.isNotBlank() && !isCreating,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (isCreating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(if (isCreating) "Creando..." else "Crear Producto")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateFirstProductScreenPreview() {
    StockWiseTheme {
        CreateFirstProductScreen(
            onProductCreated = {},
            onSkip = {}
        )
    }
}