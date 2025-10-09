package com.stoq.StockWise.shared.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
fun ReportsView(
    onMenuClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowHighlight)
    ) {
        // Header
        TabHeader(
            title = "Reportes",
            onMenuClick = onMenuClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Quick stats
        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Text(
                text = "Resumen del Mes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Ventas",
                    value = "$12,450",
                    change = "+15.3%",
                    isPositive = true,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Compras",
                    value = "$8,230",
                    change = "+8.7%",
                    isPositive = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Ganancia",
                    value = "$4,220",
                    change = "+23.1%",
                    isPositive = true,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Productos",
                    value = "124",
                    change = "+5",
                    isPositive = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Available reports
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Reportes Disponibles",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Report items
            ReportItem(
                title = "Reporte de Inventario",
                description = "Stock actual y movimientos del mes",
                date = "Actualizado hoy",
                onDownload = { /* TODO: Download report */ }
            )

            ReportItem(
                title = "Reporte de Ventas",
                description = "Ventas diarias y productos más vendidos",
                date = "Actualizado ayer",
                onDownload = { /* TODO: Download report */ }
            )

            ReportItem(
                title = "Reporte de Proveedores",
                description = "Compras por proveedor y costos",
                date = "Actualizado hace 3 días",
                onDownload = { /* TODO: Download report */ }
            )

            ReportItem(
                title = "Análisis de Rentabilidad",
                description = "Margen de ganancia por producto",
                date = "Actualizado semanalmente",
                onDownload = { /* TODO: Download report */ }
            )

            ReportItem(
                title = "Auditoría de Stock",
                description = "Diferencias de inventario y ajustes",
                date = "Actualizado mensualmente",
                onDownload = { /* TODO: Download report */ }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Generate custom report button
            Button(
                onClick = { /* TODO: Generate custom report */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generar Reporte Personalizado")
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    change: String,
    isPositive: Boolean,
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = change,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = if (isPositive) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }
    }
}

@Composable
private fun ReportItem(
    title: String,
    description: String,
    date: String,
    onDownload: () -> Unit
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = date,
                    fontSize = 12.sp,
                    color = Color(0xFFFF6F00)
                )
            }

            IconButton(
                onClick = onDownload
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Download",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}