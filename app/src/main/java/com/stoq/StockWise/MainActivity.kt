package com.stoq.StockWise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stoq.StockWise.Iam.presentation.navigation.NavigationAuth
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
                val authViewModel: AuthViewModel = viewModel()
                NavigationAuth(authViewModel = authViewModel)
            }
        }
    }
}
