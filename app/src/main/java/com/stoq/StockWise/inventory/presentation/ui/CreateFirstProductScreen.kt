package com.stoq.StockWise.inventory.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stoq.StockWise.ui.theme.StockWiseTheme

/**
 * Pantalla para crear el primer producto del usuario.
 * Es salteable y se muestra después de crear el inventario.
 */
@Composable
fun CreateFirstProductScreen(
    onProductCreated: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var salePrice by remember { mutableStateOf("") }
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
        // Icono de producto
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Producto",
            tint = Color(0xFFE65100),
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Título
        Text(
            text = "¡Agrega tu Primer Producto!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Subtítulo
        Text(
            text = "Comienza agregando un producto a tu inventario. Puedes saltar este paso y hacerlo más tarde.",
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
                    text = "Nombre del Producto",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3E2723)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                BasicTextField(
                    value = productName,
                    onValueChange = { productName = it },
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
                        if (productName.isEmpty()) {
                            Text(
                                text = "Ej: Leche Entera Gloria",
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
                    text = "Descripción",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3E2723)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                BasicTextField(
                    value = productDescription,
                    onValueChange = { productDescription = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            Color(0xFFF5F5F5),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    decorationBox = { innerTextField ->
                        if (productDescription.isEmpty()) {
                            Text(
                                text = "Descripción del producto...",
                                color = Color(0xFF9E9E9E),
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Precios
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Precio de compra
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Precio Compra",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3E2723)
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        BasicTextField(
                            value = purchasePrice,
                            onValueChange = { purchasePrice = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    Color(0xFFF5F5F5),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp),
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                if (purchasePrice.isEmpty()) {
                                    Text(
                                        text = "0.00",
                                        color = Color(0xFF9E9E9E),
                                        fontSize = 14.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                    
                    // Precio de venta
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Precio Venta",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3E2723)
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        BasicTextField(
                            value = salePrice,
                            onValueChange = { salePrice = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    Color(0xFFF5F5F5),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp),
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                if (salePrice.isEmpty()) {
                                    Text(
                                        text = "0.00",
                                        color = Color(0xFF9E9E9E),
                                        fontSize = 14.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }
                
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
                
                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Botón saltar
                    OutlinedButton(
                        onClick = onSkip,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF757575)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Saltar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    // Botón crear
                    Button(
                        onClick = {
                            if (productName.isNotBlank()) {
                                isLoading = true
                                errorMessage = null
                                // Simular creación de producto
                                onProductCreated()
                            } else {
                                errorMessage = "El nombre del producto es requerido"
                            }
                        },
                        enabled = !isLoading && productName.isNotBlank(),
                        modifier = Modifier
                            .weight(1f)
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
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Agregar",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Crear",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
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

