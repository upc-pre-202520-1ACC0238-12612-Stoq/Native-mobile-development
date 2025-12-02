package com.stoq.StockWise.dashboard.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stoq.StockWise.dashboard.domain.entities.DashboardProduct
import java.text.SimpleDateFormat
import java.util.Locale

// Colores empleados tipo POS
private val DashboardBg = Color(0xFFF5E6D3)      // Fondo beige
private val CardBg = Color.White
private val Danger = Color(0xFFE53935)           // Rojo
private val Info = Color(0xFF1E88E5)             // Azul
private val WarningColor = Color(0xFFF57C00)     // Naranja
private val TextDark = Color(0xFF4B3D37)
private val ButtonBg = Color(0xFF2196F3)

/**
 * Componente para las burbujas superiores de estadísticas.
 * Muestra iconos, colores pastel, bordes redondeados y padding amplio.
 */
@Composable
fun StatBubble(
    icon: ImageVector,
    value: String,
    label: String,
    color: Color,
    iconColor: Color
) {
    Column(
        modifier = Modifier
            .background(color, shape = MaterialTheme.shapes.large)
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(32.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            value,
            fontWeight = FontWeight.Bold,
            color = iconColor,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(4.dp))
        Text(
            label,
            color = Color(0xFF666666),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Componente para los botones grandes estilo POS.
 */
@Composable
fun PosButton(text: String, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = ButtonBg),
        elevation = ButtonDefaults.buttonElevation(3.dp)
    ) {
        Text(
            text,
            color = TextDark,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Componente para cada producto reciente.
 * Muestra nombre, fecha formateada y badge circular con stock.
 */
@Composable
fun RecentProductCard(p: DashboardProduct) {
    val formattedDate = formatDate(p.date)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = p.name,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formattedDate,
                    color = Color(0xFF666666),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Badge circular con stock
            Surface(
                shape = androidx.compose.foundation.shape.CircleShape,
                color = Color(0xFFE3F2FD),
                modifier = Modifier.size(48.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Inventory,
                            contentDescription = "Stock",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = p.stock.toString(),
                            color = Color(0xFF2196F3),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

/**
 * Función para formatear fecha de yyyy-MM-dd a dd/MM/yyyy
 */
fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date)
    } catch (e: Exception) {
        dateString // Si falla el parseo, mostrar la fecha original
    }
}

/**
 * Componente reutilizable para el TopBar de StockWise
 * Incluye el logo dividido en colores, fondo beige y menú desplegable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockWiseTopBar(
    navController: NavHostController,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Stock",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100)
                )
                Text(
                    text = "Wise",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF5D4037)
                )
            }
        },
        actions = {
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = Color(0xFF5D4037)
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "Cerrar sesión",
                                    tint = Color(0xFFD32F2F),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Cerrar sesión",
                                    color = Color(0xFFD32F2F)
                                )
                            }
                        },
                        onClick = {
                            showMenu = false
                            onLogout()
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF5E6D3)
        ),
        modifier = modifier
    )
}
