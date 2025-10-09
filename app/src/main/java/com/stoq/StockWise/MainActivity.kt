package com.stoq.StockWise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.stoq.StockWise.Iam.presentation.navigation.NavigationAuth
import com.stoq.StockWise.ui.theme.StockWiseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockWiseTheme {
                NavigationAuth()
            }
        }
    }
}
