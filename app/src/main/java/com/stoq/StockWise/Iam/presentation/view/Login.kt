package com.stoq.StockWise.Iam.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stoq.StockWise.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.ui.theme.OrangePrimary
import com.stoq.StockWise.ui.theme.BeigeSecondary

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel? = null
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo "Stock Wise"
        Text(
            text = "Stock",
            style = MaterialTheme.typography.displayLarge,
            color = OrangePrimary
        )
        Text(
            text = "Wise",
            style = MaterialTheme.typography.displayLarge,
            color = BeigeSecondary
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Título
        Text(
            text = "Inicio de sesión",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox "Recuérdame"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it }
            )
            Text(
                text = "Recuérdame",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Google
        OutlinedButton(
            onClick = { /* TODO: Google login */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar con google")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón principal
        Button(
            onClick = { /* TODO: Login logic */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangePrimary
            )
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Enlace a registro
        Row {
            Text("¿No tienes una cuenta? ")
            TextButton(
                onClick = { navController.navigate("register") }
            ) {
                Text("Regístrate")
            }
        }
    }
}