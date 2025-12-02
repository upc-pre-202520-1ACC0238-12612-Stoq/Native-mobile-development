package com.stoq.StockWise.dashboard.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stoq.StockWise.dashboard.presentation.viewmodel.DashboardViewModel
import com.stoq.StockWise.dashboard.domain.entities.DashboardProduct
import org.koin.androidx.compose.koinViewModel

// Colores empleados tipo POS
private val DashboardBg = Color(0xFFF5E6D3)      // Fondo beige
private val CardBg = Color.White
private val Danger = Color(0xFFE53935)           // Rojo
private val Info = Color(0xFF1E88E5)             // Azul
private val WarningColor = Color(0xFFF57C00)     // Naranja
private val TextDark = Color(0xFF4B3D37)
private val ButtonBg = Color(0xFF2196F3)

@Composable
fun DashboardScreen(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    val viewModel: DashboardViewModel = koinViewModel()

    val state = viewModel.uiState

    // Navigation callbacks
    val onNavigateToProducts = { navController.navigate("product_list") }
    val onNavigateToSales = { navController.navigate("sales_screen") }
    val onNavigateToCombos = { navController.navigate("combos") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DashboardBg)
    ) {
        // Top Bar con logo y menú
        StockWiseTopBar(
            navController = navController,
            onLogout = onLogout
        )

        // Mostrar indicador de carga si está cargando
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Column
        }

        // Mostrar error si existe
        state.error?.let { error ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: $error",
                    color = Danger,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            return@Column
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(16.dp)
        ) {

        // --------------------------------------------------------------------
        // 🟦 BURBUJAS SUPERIORES CON ICONOS Y COLORES PASTEL
        // --------------------------------------------------------------------
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatBubble(
                    icon = Icons.Filled.ShoppingCart,
                    value = state.stats?.totalProducts?.toString() ?: "-",
                    label = "Productos Totales",
                    color = Color(0xFFE8F5E8), // Verde pastel
                    iconColor = Color(0xFF4CAF50)
                )

                StatBubble(
                    icon = Icons.Filled.TrendingUp,
                    value = state.stats?.movementsToday?.toString() ?: "-",
                    label = "Movimientos Hoy",
                    color = Color(0xFFFFF3E0), // Naranja pastel
                    iconColor = Color(0xFFFF9800)
                )

            }
            Spacer(Modifier.height(40.dp))
        }

        // --------------------------------------------------------------------
        // 🔘 BOTONES POS GRANDES Y SIMPLES
        // --------------------------------------------------------------------
        item {
            PosButton(" Realizar Venta", onClick = onNavigateToSales)
            Spacer(Modifier.height(20.dp))

            PosButton(" Agregar Productos", onClick = onNavigateToProducts)
            Spacer(Modifier.height(20.dp))

            PosButton(" Ver combos/kits", onClick = onNavigateToCombos)
            Spacer(Modifier.height(40.dp))
        }

        // --------------------------------------------------------------------
        // 📦 PRODUCTOS RECIENTES
        // --------------------------------------------------------------------
        item {
            Text(
                "Productos Recientes",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = TextDark
            )
            Spacer(Modifier.height(16.dp))
        }

        items(state.recentProducts.take(3)) { product ->
            RecentProductCard(product)
            Spacer(Modifier.height(12.dp))
        }
        }
    }
}
