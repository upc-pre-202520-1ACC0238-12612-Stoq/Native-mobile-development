package com.stoq.StockWise.Iam.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.Iam.presentation.view.HomeScreen
import com.stoq.StockWise.Iam.presentation.view.LoginScreen
import com.stoq.StockWise.Iam.presentation.view.RegisterScreen
import com.stoq.StockWise.Iam.presentation.view.ProfileScreen
import android.widget.Toast
import com.stoq.StockWise.Iam.presentation.view.SettingsScreen
import com.stoq.StockWise.ui.theme.YellowHighlight

@Composable
fun NavigationAuth(
    authViewModel: com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel,
    modifier: Modifier = Modifier,
    onAuthSuccess: () -> Unit = {}
) {
    val navController = rememberNavController()
    val isLoggedIn = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Verificar si el usuario ya está autenticado usando el AuthViewModel
        isLoggedIn.value = authViewModel.uiState.value.isAuthenticated
    }

    Scaffold(modifier = modifier.background(YellowHighlight)) { padding ->
        NavHost(
            navController,
            startDestination = if (isLoggedIn.value) "home" else "login",
            modifier = Modifier.padding(padding)
        ) {
            loginScreen(authViewModel, navController, onAuthSuccess)
            registerScreen(authViewModel, navController, onAuthSuccess)
            homeScreen(navController)
            profileScreen(navController)
            settingsScreen(navController)
        }
    }
}

private fun NavGraphBuilder.loginScreen(
    authViewModel: com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel,
    navController: androidx.navigation.NavHostController,
    onAuthSuccess: () -> Unit
) {
    composable("login") {
        LoginScreen(
            authViewModel = authViewModel,
            goToRegister = { navController.navigate("register") },
                onLoginSuccess = {
                    // Informar al host (MainActivity) que la autenticación fue exitosa
                    onAuthSuccess()
                }
        )
    }
}

private fun NavGraphBuilder.registerScreen(
    authViewModel: com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel,
    navController: androidx.navigation.NavHostController,
    onAuthSuccess: () -> Unit
) {
    composable("register") {
        RegisterScreen(
            authViewModel = authViewModel,
            goToLogin = {
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
            },
                onRegisterSuccess = {
                    // Informar al host (MainActivity) que la autenticación fue exitosa
                    onAuthSuccess()
                }
        )
    }
}

private fun NavGraphBuilder.homeScreen(
    navController: androidx.navigation.NavHostController
) {
    composable("home") {
        HomeScreen(
            goToLogin = {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            },
            goToProfile = {
                navController.navigate("profile")
            }
        )
    }
}

private fun NavGraphBuilder.profileScreen(
    navController: androidx.navigation.NavHostController
) {
    composable("profile") {
        ProfileScreen(
            onBack = { navController.popBackStack() },
            onSettings = {
                Toast.makeText(
                    navController.context,
                    "Navegando a ajustes",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate("settings")
            }
        )
    }
}

private fun NavGraphBuilder.settingsScreen(
    navController: androidx.navigation.NavHostController
) {
    composable("settings") {
        SettingsScreen(
            onBack = { navController.popBackStack() }
        )
    }
}