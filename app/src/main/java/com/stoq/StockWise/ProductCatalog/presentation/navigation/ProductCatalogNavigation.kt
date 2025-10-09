package com.stoq.StockWise.ProductCatalog.presentation.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.ProductCatalog.presentation.view.ProductEditModal
import com.stoq.StockWise.ProductCatalog.presentation.view.ProductListScreen
import com.stoq.StockWise.ProductCatalog.presentation.view.ProductRegisterScreen

@Composable
fun ProductCatalogNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onBackToMain: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "product_list",
        modifier = modifier
    ) {
        productCatalogGraph(navController, onBackToMain)
    }
}

private fun NavGraphBuilder.productCatalogGraph(
    navController: NavController,
    onBackToMain: () -> Unit
) {
    composable("product_list") {
        ProductListScreen(
            onRegisterProduct = {
                navController.navigate("product_register")
            },
            onProductDetail = { productId ->
                // Navigation to detail screen can be added here if needed
                // For now, it just opens the edit modal from the ViewModel
            }
        )

        // Edit modal overlay
        ProductEditModal(
            onDismiss = {
                // Modal dismiss is handled internally by the ViewModel
            }
        )
    }

    composable("product_register") {
        ProductRegisterScreen(
            onBack = {
                navController.popBackStack()
            },
            onSaveSuccess = {
                navController.popBackStack()
            }
        )
    }
}

// Navigation routes constants
object ProductCatalogRoutes {
    const val PRODUCT_LIST = "product_list"
    const val PRODUCT_REGISTER = "product_register"
    const val PRODUCT_DETAIL = "product_detail"
}