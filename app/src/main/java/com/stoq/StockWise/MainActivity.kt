package com.stoq.StockWise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stoq.StockWise.presentation.navigation.NavigationAuth
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stoq.StockWise.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.ui.theme.StockWiseTheme
import com.stoq.StockWise.shared.data.local.JwtStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        JwtStorage.initialize(this)
        setContent {
            StockWiseTheme {
                    NavigationAuth()
                }
                }

        }
    }

