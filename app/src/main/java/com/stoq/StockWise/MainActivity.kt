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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.Iam.presentation.navigation.NavigationAuth
import com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.inventory.domain.services.InventoryDomainService
import com.stoq.StockWise.inventory.infrastructure.repositories.InventoryRepositoryImpl
import com.stoq.StockWise.inventory.presentation.ui.CreateFirstProductScreen
import com.stoq.StockWise.inventory.presentation.ui.CreateInventoryScreen
import com.stoq.StockWise.inventory.presentation.viewmodels.InventorySetupViewModel
import com.stoq.StockWise.inventory.presentation.viewmodels.InventorySetupUiState
import com.stoq.StockWise.product.presentation.ui.ProductScreen
import com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel
import com.stoq.StockWise.shared.infrastructure.network.NetworkClient
import com.stoq.StockWise.ui.theme.StockWiseTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    
    private val authViewModel: AuthViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            StockWiseTheme {
                MainApp(authViewModel)
            }
        }
    }
}

@Composable
fun MainApp(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    
    // ViewModels
    val inventorySetupViewModel: InventorySetupViewModel = viewModel {
        val inventoryRepository = InventoryRepositoryImpl()
        val inventoryDomainService = InventoryDomainService(inventoryRepository)
        InventorySetupViewModel(inventoryDomainService)
    }
    
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
                    modifier = Modifier.padding(innerPadding)
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
            
            // Pantalla principal de productos
            composable("main") {
                val productApiService = NetworkClient.createProductApiService()
                val productRepository = com.stoq.StockWise.product.infrastructure.repositories.ProductRepositoryImpl(productApiService)
                val productViewModel = ProductViewModel(productRepository)
                
                ProductScreen(
                    viewModel = productViewModel,
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