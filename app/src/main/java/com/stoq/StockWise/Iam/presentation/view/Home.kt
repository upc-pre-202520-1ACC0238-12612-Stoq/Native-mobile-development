package com.stoq.StockWise.Iam.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stoq.StockWise.ui.theme.YellowHighlight

@Composable
fun HomeScreen(
    goToLogin: () -> Unit,
    goToDashboard: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowHighlight)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Bienvenido a StockWise!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Inicio de sesión exitoso",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(32.dp))

                // ⭐⭐⭐ BOTÓN PARA IR AL DASHBOARD ⭐⭐⭐
                Button(
                    onClick = goToDashboard,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ir al Dashboard")
                }

                Spacer(Modifier.height(16.dp))

                // Botón para cerrar sesión
                Button(
                    onClick = goToLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar sesión")
                }
            }
        }
    }
}
