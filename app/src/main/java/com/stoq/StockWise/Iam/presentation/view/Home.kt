package com.stoq.StockWise.Iam.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stoq.StockWise.shared.data.local.JwtStorage
import com.stoq.StockWise.shared.presentation.views.MainTabbedView
import com.stoq.StockWise.ui.theme.YellowHighlight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    goToLogin: () -> Unit
) {
    MainTabbedView(
        onLogout = {
            JwtStorage.clearToken()
            goToLogin()
        }
    )
}