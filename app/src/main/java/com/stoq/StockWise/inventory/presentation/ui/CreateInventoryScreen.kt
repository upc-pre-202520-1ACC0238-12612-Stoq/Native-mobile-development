package com.stoq.StockWise.inventory.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stoq.StockWise.ui.theme.StockWiseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateInventoryScreen(
    modifier: Modifier = Modifier,
    onInventoryCreated: () -> Unit,
    onShowBranchesMap: () -> Unit = {}
) {
    var inventoryName by remember { mutableStateOf("") }
    var inventoryDescription by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf("") }
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
            text = "Crear tu Inventario",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Configura tu primer inventario para comenzar a gestionar tus productos",
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
                // Campo de nombre
                OutlinedTextField(
                    value = inventoryName,
                    onValueChange = { inventoryName = it },
                    label = { Text("Nombre del inventario") },
                    placeholder = { Text("Mi Inventario Principal") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Campo de ubicación
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = selectedLocation,
                        onValueChange = { selectedLocation = it },
                        label = { Text("Ubicación/Sede") },
                        placeholder = { Text("Selecciona una sede") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        readOnly = true
                    )

                    Button(
                        onClick = onShowBranchesMap,
                        modifier = Modifier.height(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Seleccionar ubicación"
                        )
                    }
                }

                // Campo de descripción
                OutlinedTextField(
                    value = inventoryDescription,
                    onValueChange = { inventoryDescription = it },
                    label = { Text("Descripción (opcional)") },
                    placeholder = { Text("Descripción de mi inventario...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                // Botón de crear
                Button(
                    onClick = {
                        if (inventoryName.isNotBlank()) {
                            isCreating = true
                            // Simular creación del inventario
                            onInventoryCreated()
                        }
                    },
                    enabled = inventoryName.isNotBlank() && !isCreating,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isCreating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(if (isCreating) "Creando..." else "Crear Inventario")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateInventoryScreenPreview() {
    StockWiseTheme {
        CreateInventoryScreen(
            onInventoryCreated = {}
        )
    }
}