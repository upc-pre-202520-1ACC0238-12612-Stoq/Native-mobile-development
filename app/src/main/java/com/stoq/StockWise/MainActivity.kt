package com.stoq.StockWise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.presentation.navigation.authNavigation
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
<<<<<<< HEAD
import com.stoq.StockWise.product.infrastructure.repositories.ProductRepositoryImpl
import com.stoq.StockWise.product.presentation.ui.ProductScreen
import com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel
import com.stoq.StockWise.sharedkernel.infrastructure.network.NetworkClient
import com.stoq.StockWise.ui.theme.StoqTheme
=======
import com.stoq.StockWise.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.ui.theme.StockWiseTheme
>>>>>>> 466fbb9 (feat: primera version iam)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Configurar dependencias
        val productApiService = NetworkClient.createProductApiService()
        val productRepository = ProductRepositoryImpl(productApiService)
        val productViewModel = ProductViewModel(productRepository)
        
        setContent {
<<<<<<< HEAD
            StoqTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    ProductScreen(
                        viewModel = productViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
=======
            StockWiseTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    authNavigation(navController,authViewModel)
>>>>>>> 466fbb9 (feat: primera version iam)
                }
            }
        }
    }
<<<<<<< HEAD
=======
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StockWiseTheme {
        Greeting("Android")
    }
>>>>>>> 466fbb9 (feat: primera version iam)
}