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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.Iam.presentation.di.PresentationModule
import com.stoq.StockWise.Iam.presentation.view.HomeScreen
import com.stoq.StockWise.Iam.presentation.view.LoginScreen
import com.stoq.StockWise.Iam.presentation.view.RegisterScreen
import com.stoq.StockWise.shared.data.local.JwtStorage
import com.stoq.StockWise.ui.theme.YellowHighlight

@Preview
@Composable
fun NavigationAuth() {
    val navController = rememberNavController()
    val authViewModel = PresentationModule.getAuthViewModel()
    val isLoggedIn = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoggedIn.value = JwtStorage.getToken() != null
    }

    Scaffold(modifier = Modifier.background(YellowHighlight)) { padding ->
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