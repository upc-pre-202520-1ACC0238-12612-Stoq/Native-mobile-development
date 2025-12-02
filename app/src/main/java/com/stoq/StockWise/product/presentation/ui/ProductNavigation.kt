package com.stoq.StockWise.product.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel
import com.stoq.StockWise.product.presentation.viewmodels.ScanProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductNavigation(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "product_list"
    ) {

        /**  Pantalla principal: listado de productos */
        composable("product_list") {
            val productViewModel: ProductViewModel = koinViewModel()

            ProductScreen(
                viewModel = productViewModel,
                navController = navController,
                onLogout = onLogout
            )
        }

        /** Pantalla de escaneo con cámara (Plan D) */
        composable(route = "scan_product") {
            val scanViewModel: ScanProductViewModel = koinViewModel()

            ScanProductScreen(
                navController = navController,
                viewModel = scanViewModel
            )
        }

    }
}
