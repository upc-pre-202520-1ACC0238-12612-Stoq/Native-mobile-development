package com.stoq.StockWise.shared.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stoq.StockWise.shared.presentation.components.TabHeader
import com.stoq.StockWise.ui.theme.YellowHighlight

@Composable
fun InventoryView(
    onMenuClick: () -> Unit = {},
    onAddMovement: () -> Unit = {},
    onGenerateReport: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowHighlight)
    ) {
        // Header
        TabHeader(
            title = "Inventario",
            onMenuClick = onMenuClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            // Summary cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryCard(
                    title = "Total Productos",
                    value = "124",
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Stock Bajo",
                    value = "8",
                    color = Color(0xFFFF9800),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryCard(
                    title = "Sin Stock",
                    value = "3",
                    color = Color(0xFFF44336),
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Valor Total",
                    value = "$2,450",
                    color = Color(0xFF2196F3),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Recent movements
            Text(
                text = "Movimientos Recientes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Movement list
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MovementItem(
                    product = "Galleta Chocolate",
                    type = "Entrada",
                    quantity = "+50",
                    date = "Hoy, 14:30"
                )
                MovementItem(
                    product = "Refresco Cola",
                    type = "Salida",
                    quantity = "-25",
                    date = "Hoy, 12:15"
                )
                MovementItem(
                    product = "Pan Integral",
                    type = "Entrada",
                    quantity = "+100",
                    date = "Ayer, 18:45"
                )
                MovementItem(
                    product = "Leche 1L",
                    type = "Salida",
                    quantity = "-30",
                    date = "Ayer, 09:20"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Quick actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { onGenerateReport() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Generar Reporte")
                }
                Button(
                    onClick = { onAddMovement() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F)
                    )
                ) {
                    Text("Añadir Movimiento")
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun MovementItem(
    product: String,
    type: String,
    quantity: String,
    date: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = date,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = quantity,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (quantity.startsWith("+")) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
                Text(
                    text = type,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}