package com.stoq.StockWise.inventory.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stoq.StockWise.ui.theme.StockWiseTheme

/**
 * Pantalla para crear el inventario inicial del usuario.
 * Se muestra cuando el usuario no tiene inventario configurado.
 */
@Composable
fun CreateInventoryScreen(
    onInventoryCreated: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inventoryName by remember { mutableStateOf("") }
    var inventoryDescription by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5E6D3))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icono de inventario
        Icon(
            imageVector = Icons.Default.List,
            contentDescription = "Inventario",
            tint = Color(0xFFE65100),
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Título
        Text(
            text = "¡Crea tu Inventario!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Subtítulo
        Text(
            text = "Para comenzar a gestionar tus productos, necesitas crear tu primer inventario.",
            fontSize = 16.sp,
            color = Color(0xFF5D4037),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Formulario
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Campo nombre
                Text(
                    text = "Nombre del Inventario",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3E2723)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                BasicTextField(
                    value = inventoryName,
                    onValueChange = { inventoryName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            Color(0xFFF5F5F5),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (inventoryName.isEmpty()) {
                            Text(
                                text = "Ej: Mi Tienda, Almacén Principal",
                                color = Color(0xFF9E9E9E),
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Campo descripción
                Text(
                    text = "Descripción (Opcional)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3E2723)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                BasicTextField(
                    value = inventoryDescription,
                    onValueChange = { inventoryDescription = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(
                            Color(0xFFF5F5F5),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    decorationBox = { innerTextField ->
                        if (inventoryDescription.isEmpty()) {
                            Text(
                                text = "Describe tu inventario...",
                                color = Color(0xFF9E9E9E),
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Mensaje de error
                errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = Color(0xFFD32F2F),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Botón crear
                Button(
                    onClick = {
                        if (inventoryName.isNotBlank()) {
                            isLoading = true
                            errorMessage = null
                            // Simular creación de inventario
                            onInventoryCreated()
                        } else {
                            errorMessage = "El nombre del inventario es requerido"
                        }
                    },
                    enabled = !isLoading && inventoryName.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE65100)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = "Crear Inventario",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
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

