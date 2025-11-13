package com.stoq.StockWise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.stoq.StockWise.Iam.presentation.navigation.NavigationAuth
import com.stoq.StockWise.shared.data.local.JwtStorage
import com.stoq.StockWise.ui.theme.StockWiseTheme
import org.koin.androidx.compose.koinViewModel
import com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        JwtStorage.initialize(this)
        
        setContent {
            StockWiseTheme {
                val authViewModel: AuthViewModel = koinViewModel()
                NavigationAuth(authViewModel = authViewModel)
            }
        }
    }
}
