package com.stoq.StockWise.dashboard.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import com.stoq.StockWise.dashboard.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardMain(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    DashboardScreen(
        navController = navController,
        onLogout = onLogout
    )
}