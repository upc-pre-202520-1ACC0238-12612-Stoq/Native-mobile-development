package com.stoq.StockWise.product.presentation.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.ui.theme.StockWiseTheme

// Colores de la paleta
private val OrangePrimary = Color(0xFFFF8A00)
private val BrownTitle = Color(0xFF5D4037)
private val BeigeBackground = Color(0xFFF5E9D3)
private val GreenSuccess = Color(0xFF4CAF50)
private val GrayText = Color(0xFF757575)

/**
 * Modal flotante para mostrar detalles completos de un producto
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailModal(
    product: Product?,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onEditProduct: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isVisible || product == null) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    // Header con botón cerrar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = BeigeBackground.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = BrownTitle,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Contenido scrollable
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // Nombre del producto
                        Text(
                            text = product.name ?: "Sin nombre",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrownTitle,
                            lineHeight = 32.sp
                        )

                        // Información básica
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = BeigeBackground.copy(alpha = 0.3f)
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Categoría y Unidad
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    // Categoría
                                    if (!product.categoryName.isNullOrEmpty()) {
                                        InfoItem(
                                            label = "Categoría",
                                            value = product.categoryName,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }

                                    // Unidad
                                    if (!product.unitName.isNullOrEmpty()) {
                                        InfoItem(
                                            label = "Unidad",
                                            value = "${product.unitName} (${product.unitAbbreviation ?: ""})",
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }

                                // Tags
                                if (!product.tags.isNullOrEmpty()) {
                                    Text(
                                        text = "Tags",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = BrownTitle
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        product.tags.forEach { tag ->
                                            AssistChip(
                                                onClick = { /* No action */ },
                                                label = {
                                                    Text(
                                                        text = tag.name ?: "",
                                                        fontSize = 12.sp
                                                    )
                                                },
                                                colors = AssistChipDefaults.assistChipColors(
                                                    containerColor = OrangePrimary.copy(alpha = 0.1f),
                                                    labelColor = OrangePrimary
                                                ),
                                                border = null
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Descripción completa
                        if (!product.description.isNullOrEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = BeigeBackground.copy(alpha = 0.2f)
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Descripción",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = BrownTitle,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Text(
                                        text = product.description,
                                        fontSize = 14.sp,
                                        color = GrayText,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }

                        // Información de inventario y precios
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Stock actual
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = GreenSuccess.copy(alpha = 0.1f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Stock Actual",
                                        fontSize = 12.sp,
                                        color = GreenSuccess,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = product.salePrice?.toInt()?.toString() ?: "0",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GreenSuccess
                                    )
                                }
                            }

                            // Precios
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = OrangePrimary.copy(alpha = 0.1f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Precio de compra
                                    if (product.purchasePrice != null) {
                                        PriceItem(
                                            label = "Compra",
                                            price = product.purchasePrice,
                                            color = GrayText
                                        )
                                    }

                                    // Precio de venta
                                    if (product.salePrice != null) {
                                        PriceItem(
                                            label = "Venta",
                                            price = product.salePrice,
                                            color = OrangePrimary
                                        )
                                    }
                                }
                            }
                        }

                        // Notas internas
                        if (!product.internalNotes.isNullOrEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = BeigeBackground.copy(alpha = 0.2f)
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Notas Internas",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = BrownTitle,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Text(
                                        text = product.internalNotes,
                                        fontSize = 14.sp,
                                        color = GrayText,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Botón editar
                        Button(
                            onClick = { onEditProduct(product) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                OrangePrimary,
                                                OrangePrimary.copy(alpha = 0.8f)
                                            )
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Editar Producto",
                                        color = Color.White,
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
    }
}

/**
 * Componente para mostrar información con etiqueta
 */
@Composable
private fun InfoItem(
    label: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = BrownTitle,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value ?: "N/A",
            fontSize = 14.sp,
            color = GrayText,
            fontWeight = FontWeight.Normal
        )
    }
}

/**
 * Componente para mostrar precios
 */
@Composable
private fun PriceItem(
    label: String,
    price: Double?,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = color,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = price?.let { "$${String.format("%.2f", it)}" } ?: "$0.00",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailModalPreview() {
    val sampleProduct = Product(
        id = 1,
        name = "Agua Mineral San Luis",
        description = "Agua mineral natural premium, extraída de manantiales puros en las montañas. Perfecta para consumo diario, sin gas y con un sabor refrescante único.",
        purchasePrice = 12.50,
        salePrice = 18.00,
        internalNotes = "Proveedor: Distribuidora ABC\nPróximo pedido: 15 unidades\nDescuento disponible: 5% en compras mayores a 50 unidades",
        categoryId = 1,
        categoryName = "Bebidas",
        unitId = 1,
        unitName = "Botella",
        unitAbbreviation = "btl",
        tags = listOf(
            com.stoq.StockWise.product.domain.entities.Tag(1, "Natural"),
            com.stoq.StockWise.product.domain.entities.Tag(2, "Sin Gas"),
            com.stoq.StockWise.product.domain.entities.Tag(3, "Premium")
        )
    )

    StockWiseTheme {
        ProductDetailModal(
            product = sampleProduct,
            isVisible = true,
            onDismiss = {},
            onEditProduct = {}
        )
    }
}
