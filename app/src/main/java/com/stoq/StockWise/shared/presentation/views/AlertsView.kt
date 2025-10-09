package com.stoq.StockWise.shared.presentation.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
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
fun AlertsView(
    onMenuClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowHighlight)
    ) {
        // Header
        TabHeader(
            title = "Alertas",
            onMenuClick = onMenuClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Alert filters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilterChip(
                selected = true,
                onClick = { /* TODO: Filter */ },
                label = { Text("Todas", fontSize = 12.sp) }
            )
            FilterChip(
                selected = false,
                onClick = { /* TODO: Filter */ },
                label = { Text("Críticas", fontSize = 12.sp) }
            )
            FilterChip(
                selected = false,
                onClick = { /* TODO: Filter */ },
                label = { Text("Stock Bajo", fontSize = 12.sp) }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Alerts list
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Critical alert
            AlertCard(
                title = "Sin Stock - Galleta Chocolate",
                message = "Producto agotado. Necesita reabastecimiento inmediato.",
                severity = AlertSeverity.CRITICAL,
                time = "Hace 10 minutos"
            )

            // Low stock alert
            AlertCard(
                title = "Stock Bajo - Refresco Cola",
                message = "Quedan solo 5 unidades. Punto de reorden alcanzado.",
                severity = AlertSeverity.HIGH,
                time = "Hace 1 hora"
            )

            // Warning alert
            AlertCard(
                title = "Próxima Caducidad - Lácteos",
                message = "10 productos vencen en los próximos 3 días.",
                severity = AlertSeverity.MEDIUM,
                time = "Hace 2 horas"
            )

            // Info alert
            AlertCard(
                title = "Reporte Diario Disponible",
                message = "El reporte de inventario del día ha sido generado.",
                severity = AlertSeverity.LOW,
                time = "Hace 3 horas"
            )

            // Low stock alert
            AlertCard(
                title = "Stock Bajo - Pan Integral",
                message = "Quedan solo 8 unidades. Considerar reabastecer.",
                severity = AlertSeverity.HIGH,
                time = "Ayer"
            )

            Spacer(modifier = Modifier.weight(1f))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { /* TODO: Mark all as read */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Marcar Todas Leídas")
                }
                Button(
                    onClick = { /* TODO: Configure alerts */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F)
                    )
                ) {
                    Text("Configurar Alertas")
                }
            }
        }
    }
}

enum class AlertSeverity {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW
}

@Composable
private fun AlertCard(
    title: String,
    message: String,
    severity: AlertSeverity,
    time: String
) {
    val (backgroundColor, iconColor, borderColor) = when (severity) {
        AlertSeverity.CRITICAL -> Triple(Color(0xFFFFEBEE), Color(0xFFD32F2F), Color(0xFFD32F2F))
        AlertSeverity.HIGH -> Triple(Color(0xFFFFF3E0), Color(0xFFFF9800), Color(0xFFFF9800))
        AlertSeverity.MEDIUM -> Triple(Color(0xFFFFF8E1), Color(0xFFFFC107), Color(0xFFFFC107))
        AlertSeverity.LOW -> Triple(Color(0xFFE3F2FD), Color(0xFF2196F3), Color(0xFF2196F3))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}