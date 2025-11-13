package com.stoq.StockWise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.activity.enableEdgeToEdge
import com.stoq.StockWise.Iam.presentation.navigation.authNavigation
import com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel
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
                val authViewModel: AuthViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    authNavigation(navController, authViewModel)
                }
            }
        }
    }
}
