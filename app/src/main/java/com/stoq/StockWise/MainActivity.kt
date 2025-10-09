package com.stoq.StockWise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stoq.StockWise.product.infrastructure.repositories.ProductRepositoryImpl
import com.stoq.StockWise.product.presentation.ui.ProductScreen
import com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel
import com.stoq.StockWise.sharedkernel.infrastructure.network.NetworkClient
import com.stoq.StockWise.ui.theme.StoqTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Configurar dependencias
        val productApiService = NetworkClient.createProductApiService()
        val productRepository = ProductRepositoryImpl(productApiService)
        val productViewModel = ProductViewModel(productRepository)
        
        setContent {
            StoqTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    ProductScreen(
                        viewModel = productViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}