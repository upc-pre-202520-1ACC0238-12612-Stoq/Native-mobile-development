package com.stoq.StockWise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.product.presentation.ui.ProductNavigation
import com.stoq.StockWise.shared.data.local.JwtStorage
import com.stoq.StockWise.ui.theme.StockWiseTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        JwtStorage.initialize(this)

        setContent {
            StockWiseTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductNavigation(
                        navController = navController,
                        onLogout = { }
                    )
                }
            }
        }
    }
}
