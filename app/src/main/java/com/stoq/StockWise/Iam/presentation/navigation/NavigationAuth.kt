package com.stoq.StockWise.Iam.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.Iam.presentation.view.HomeScreen
import com.stoq.StockWise.Iam.presentation.view.LoginScreen
import com.stoq.StockWise.Iam.presentation.view.RegisterScreen
import com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.dashboard.presentation.ui.DashboardMain
import com.stoq.StockWise.product.presentation.ui.ProductNavigation
import com.stoq.StockWise.combo.presentation.ui.CombosScreen
import com.stoq.StockWise.combo.presentation.ui.AddComboScreen
import com.stoq.StockWise.sales.presentation.ui.SalesScreen
import com.stoq.StockWise.ui.theme.YellowHighlight

@Composable
fun NavigationAuth(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val isLoggedIn = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoggedIn.value = authViewModel.uiState.value.isAuthenticated
    }

    Scaffold(modifier = modifier.background(YellowHighlight)) { padding ->

        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn.value) "home" else "login",
            modifier = Modifier.padding(padding)
        ) {

            // -----------------------
            // LOGIN
            // -----------------------
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

            // -----------------------
            // REGISTER
            // -----------------------
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

            // -----------------------
            // HOME (Pantalla intermedia)
            // -----------------------
            composable("home") {
                HomeScreen(
                    goToLogin = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    goToDashboard = {
                        navController.navigate("dashboard")
                    }
                )
            }

            // -----------------------
            // DASHBOARD
            // -----------------------
            composable("dashboard") {
                DashboardMain(
                    navController = navController,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // -----------------------
            // PRODUCTS
            // -----------------------
            composable("products") {
                ProductNavigation(
                    navController = navController,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // -----------------------
            // PRODUCT LIST (direct navigation)
            // -----------------------
            composable("product_list") {
                val productViewModel: com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel = org.koin.androidx.compose.koinViewModel()

                com.stoq.StockWise.product.presentation.ui.ProductScreen(
                    viewModel = productViewModel,
                    navController = navController,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // -----------------------
            // COMBOS
            // -----------------------
            composable("combos") {
                CombosScreen(navController = navController)
            }

            // -----------------------
            // ADD COMBO
            // -----------------------
            composable("add_combo") {
                AddComboScreen(navController = navController)
            }

            // -----------------------
            // SALES SCREEN
            // -----------------------
            composable("sales_screen") {
                SalesScreen(navController = navController)
            }
        }
    }
}

// EXTENSIÓN OPCIONAL
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
        HomeScreen(
            goToLogin = {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            },
            goToDashboard = {
                navController.navigate("dashboard")
            }
        )
    }

    composable("dashboard") {
        DashboardMain(
            navController = navController,
            onLogout = {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }
}
