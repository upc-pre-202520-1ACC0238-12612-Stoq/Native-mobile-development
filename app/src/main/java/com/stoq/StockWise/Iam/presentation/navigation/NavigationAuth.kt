package com.stoq.StockWise.Iam.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.Iam.presentation.view.HomeScreen
import com.stoq.StockWise.Iam.presentation.view.LoginScreen
import com.stoq.StockWise.Iam.presentation.view.RegisterScreen
import com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.ui.theme.YellowHighlight

@Preview
@Composable
fun NavigationAuth(
    authViewModel: com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel,
    modifier: Modifier = Modifier
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
            composable("login") {
                LoginScreen(
                    authViewModel = authViewModel,
                    goToRegister = { navController.navigate("register") },
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable("register") {
                RegisterScreen(
                    authViewModel = authViewModel,
                    goToLogin = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onRegisterSuccess = {
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable("home") {
                HomeScreen(goToLogin = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                })
            }
        }
    }
}

/**
 * Función de extensión para agregar las rutas de autenticación a un NavHost
 */
fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    composable("login") {
        LoginScreen(
            authViewModel = authViewModel,
            goToRegister = { navController.navigate("register") },
            onLoginSuccess = {
                navController.navigate("home") {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }

    composable("register") {
        RegisterScreen(
            authViewModel = authViewModel,
            goToLogin = {
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
            },
            onRegisterSuccess = {
                navController.navigate("home") {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }

    composable("home") {
        HomeScreen(goToLogin = {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        })
    }
}