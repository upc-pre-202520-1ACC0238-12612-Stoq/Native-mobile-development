package com.stoq.StockWise.product.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.entities.Tag
import com.stoq.StockWise.ui.theme.StockWiseTheme

// Colores de la paleta
private val OrangePrimary = Color(0xFFFF8A00)
private val BrownTitle = Color(0xFF5D4037)
private val BeigeBackground = Color(0xFFF5E9D3)
private val GreenSuccess = Color(0xFF4CAF50)
private val GrayText = Color(0xFF757575)

/**
 * Tarjeta moderna de producto con diseño elegante
 */
@Composable
fun ProductCardModern(
    name: String,
    description: String?,
    category: String?,
    stock: Int,
    tags: List<String>,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header con nombre y stock badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Nombre del producto
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrownTitle,
                    modifier = Modifier.weight(1f),
                    lineHeight = 22.sp
                )

                // Badge de stock verde
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = GreenSuccess,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = stock.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Subtítulo: categoría
            if (!category.isNullOrEmpty()) {
                Text(
                    text = category,
                    fontSize = 14.sp,
                    color = GrayText,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Descripción (si existe)
            if (!description.isNullOrEmpty()) {
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = GrayText.copy(alpha = 0.8f),
                    lineHeight = 18.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Tags como chips modernos
            if (tags.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    tags.take(3).forEach { tag ->
                        AssistChip(
                            onClick = { /* No action */ },
                            label = {
                                Text(
                                    text = tag,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = OrangePrimary.copy(alpha = 0.1f),
                                labelColor = OrangePrimary
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = OrangePrimary.copy(alpha = 0.2f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                    if (tags.size > 3) {
                        AssistChip(
                            onClick = { /* No action */ },
                            label = {
                                Text(
                                    text = "+${tags.size - 3}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = GrayText.copy(alpha = 0.1f),
                                labelColor = GrayText
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = GrayText.copy(alpha = 0.2f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Botón "Detalle" ocupando todo el ancho
            Button(
                onClick = onDetailClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    OrangePrimary.copy(alpha = 0.8f),
                                    OrangePrimary.copy(alpha = 0.6f)
                                )
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Detalle",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardModernPreview() {
    StockWiseTheme {
        ProductCardModern(
            name = "Agua San Luis",
            description = "Agua mineral natural sin gas, botella de 500ml perfecta para el día a día",
            category = "Bebidas",
            stock = 150,
            tags = listOf("Local", "Natural", "Sin Gas", "Premium"),
            onDetailClick = {}
        )
    }
}
