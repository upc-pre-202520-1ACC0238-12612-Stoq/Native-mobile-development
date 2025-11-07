package com.stoq.StockWise.inventory.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stoq.StockWise.R
import com.stoq.StockWise.ui.theme.StockWiseTheme
import com.stoq.StockWise.ui.theme.OrangePrimary
import com.stoq.StockWise.ui.theme.BeigeSecondary
import com.stoq.StockWise.ui.theme.YellowHighlight

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
            .background(YellowHighlight)
            .verticalScroll(rememberScrollState())
    ) {
        // Header con logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OrangePrimary)
                .padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Usar el ícono de inventario que ya creamos
                Image(
                    painter = painterResource(id = R.drawable.ic_inventory),
                    contentDescription = "Inventario",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Crear Inventario",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Configura tu espacio de trabajo",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }

        // Formulario
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Campo de nombre
                Column {
                    Text(
                        text = "Nombre del Inventario",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OrangePrimary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = inventoryName,
                        onValueChange = { inventoryName = it },
                        placeholder = { Text("Ej: Mi Tienda Principal") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = OrangePrimary,
                            focusedLabelColor = OrangePrimary,
                            cursorColor = OrangePrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Campo de ubicación
                Column {
                    Text(
                        text = "Ubicación",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OrangePrimary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = selectedLocation,
                            onValueChange = { selectedLocation = it },
                            placeholder = { Text("Selecciona una sede en el mapa") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            readOnly = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = OrangePrimary,
                                focusedLabelColor = OrangePrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Button(
                            onClick = onShowBranchesMap,
                            modifier = Modifier
                                .height(56.dp)
                                .width(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BeigeSecondary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Seleccionar ubicación",
                                tint = OrangePrimary
                            )
                        }
                    }
                }

                // Campo de descripción
                Column {
                    Text(
                        text = "Descripción (Opcional)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OrangePrimary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = inventoryDescription,
                        onValueChange = { inventoryDescription = it },
                        placeholder = { Text("Describe el propósito de este inventario...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        maxLines = 6,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = OrangePrimary,
                            focusedLabelColor = OrangePrimary,
                            cursorColor = OrangePrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (isCreating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                    Text(
                        text = if (isCreating) "Creando Inventario..." else "Crear Inventario",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Texto de ayuda
                if (inventoryName.isBlank()) {
                    Text(
                        text = "💡 Ingresa un nombre para tu inventario para continuar",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Información adicional
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            colors = CardDefaults.cardColors(containerColor = BeigeSecondary.copy(alpha = 0.3f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Información",
                    modifier = Modifier.size(24.dp),
                    tint = OrangePrimary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Tu inventario será el espacio principal para gestionar todos tus productos y stock",
                    style = MaterialTheme.typography.bodySmall,
                    color = OrangePrimary
                )
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