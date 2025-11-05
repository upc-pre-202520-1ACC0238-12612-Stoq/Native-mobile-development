package com.stoq.StockWise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.Iam.presentation.navigation.NavigationAuth
import com.stoq.StockWise.Iam.presentation.view.HomeScreen
import com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.inventory.presentation.ui.CreateFirstProductScreen
import com.stoq.StockWise.inventory.presentation.ui.CreateInventoryScreen
import com.stoq.StockWise.inventory.presentation.viewmodels.InventorySetupViewModel
import com.stoq.StockWise.inventory.presentation.viewmodels.InventorySetupUiState
import com.stoq.StockWise.product.presentation.ui.ProductScreen
import com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel
import com.stoq.StockWise.ui.theme.StockWiseTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            StockWiseTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    
    // ViewModels - Usar Koin para inyección de dependencias
    val authViewModel: AuthViewModel = koinViewModel()
    val inventorySetupViewModel: InventorySetupViewModel = koinViewModel()
    
    // Observar estado de autenticación
    val authState by authViewModel.uiState.collectAsState()
    val inventorySetupState by inventorySetupViewModel.uiState.collectAsState()
    
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = determineStartDestination(authState, inventorySetupState)
        ) {
            // Pantalla de autenticación
            composable("auth") {
                NavigationAuth(
                    authViewModel = authViewModel,
                    modifier = Modifier.padding(innerPadding),
                    onAuthSuccess = {
                        // Navegar al home principal del host y eliminar la pila de auth
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                )
            }
            
            // Pantalla de creación de inventario
            composable("create_inventory") {
                CreateInventoryScreen(
                    onInventoryCreated = {
                        inventorySetupViewModel.completeFirstProductSetup()
                        navController.navigate("main") {
                            popUpTo("create_inventory") { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            
            // Pantalla de creación del primer producto
            composable("create_first_product") {
                CreateFirstProductScreen(
                    onProductCreated = {
                        inventorySetupViewModel.completeFirstProductSetup()
                        navController.navigate("main") {
                            popUpTo("create_first_product") { inclusive = true }
                        }
                    },
                    onSkip = {
                        inventorySetupViewModel.completeFirstProductSetup()
                        navController.navigate("main") {
                            popUpTo("create_first_product") { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            
            // Pantalla principal (Home/dashboard)
            composable("home") {
                HomeScreen(
                    goToLogin = {
                        authViewModel.logout()
                        navController.navigate("auth") { popUpTo(0) { inclusive = true } }
                    },
                    goToProfile = { navController.navigate("main") },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            // Pantalla principal de productos (Inventario)
            composable("main") {
                val productViewModel: ProductViewModel = koinViewModel()

                ProductScreen(
                    viewModel = productViewModel,
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate("auth") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

/**
 * Determina la pantalla inicial basada en el estado de autenticación y configuración
 */
@Composable
fun determineStartDestination(
    authState: com.stoq.StockWise.Iam.presentation.viewmodels.AuthUiState,
    inventorySetupState: InventorySetupUiState
): String {
    return when {
        // Usuario no autenticado
        !authState.isAuthenticated -> "auth"
        
        // Usuario autenticado pero necesita crear inventario
        inventorySetupState.needsInventorySetup -> "create_inventory"
        
        // Usuario autenticado, tiene inventario pero necesita crear primer producto
        inventorySetupState.needsFirstProduct -> "create_first_product"
        
        // Usuario listo para usar la aplicación
        inventorySetupState.isReady -> "main"
        
        // Estado de carga o error, mostrar pantalla principal
        else -> "main"
    }
}