package com.stoq.StockWise.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.stoq.StockWise.Iam.presentation.view.LoginScreen
import com.stoq.StockWise.Iam.presentation.view.RegisterScreen
import com.stoq.StockWise.presentation.viewmodels.AuthViewModel

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    composable("login") {
        LoginScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
    composable("register") {
        RegisterScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}