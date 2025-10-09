package com.stoq.StockWise.shared.presentation.views

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.ProductCatalog.presentation.view.ProductRegisterScreen
import com.stoq.StockWise.shared.presentation.components.MainTabNavigation
import com.stoq.StockWise.shared.presentation.components.MainTabs

sealed class Screen(val route: String) {
    object AddProduct : Screen("add_product")
    object Inventory : Screen("inventory")
    object Alerts : Screen("alerts")
    object Reports : Screen("reports")
    object ProductRegister : Screen("product_register")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
}

@Composable
fun MainTabbedView(
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()
    var selectedTabIndex by remember { mutableStateOf(0) }

    MainTabNavigation(
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { index ->
            selectedTabIndex = index
            val route = when (index) {
                0 -> Screen.AddProduct.route
                1 -> Screen.Inventory.route
                2 -> Screen.Alerts.route
                3 -> Screen.Reports.route
                else -> Screen.AddProduct.route
            }
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        tabs = MainTabs
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.AddProduct.route,
            modifier = Modifier
        ) {
            composable(Screen.AddProduct.route) {
                AddProductView(
                    onMenuClick = { onLogout() },
                    onRegisterProduct = {
                        navController.navigate(Screen.ProductRegister.route)
                    },
                    onProductDetail = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }

            composable(Screen.Inventory.route) {
                InventoryView(
                    onMenuClick = { onLogout() },
                    onAddMovement = {
                        // Navegar a pantalla de añadir movimiento
                    },
                    onGenerateReport = {
                        navController.navigate(Screen.Reports.route)
                    }
                )
            }

            composable(Screen.Alerts.route) {
                AlertsView(
                    onMenuClick = { onLogout() }
                )
            }

            composable(Screen.Reports.route) {
                ReportsView(
                    onMenuClick = { onLogout() }
                )
            }

            composable(Screen.ProductRegister.route) {
                ProductRegisterScreen(
                    onBack = { navController.popBackStack() },
                    onSaveSuccess = { navController.popBackStack() }
                )
            }

            composable(Screen.ProductDetail.route) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                ProductDetailScreen(
                    productId = productId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}