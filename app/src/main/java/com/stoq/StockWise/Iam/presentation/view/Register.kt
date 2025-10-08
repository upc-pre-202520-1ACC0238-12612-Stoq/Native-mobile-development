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

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel? = null
) {
    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Regístrate",
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

        // Campo de nombre completo
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Nombres y apellidos") },
            modifier = Modifier.fillMaxWidth(),
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

        // Checkbox términos
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = acceptTerms,
                onCheckedChange = { acceptTerms = it }
            )
            Text(
                text = "Acepto los términos y condiciones",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Google
        OutlinedButton(
            onClick = { /* TODO: Google register */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse con google")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón principal
        Button(
            onClick = { /* TODO: Register logic */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = acceptTerms,
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangePrimary
            )
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Enlace a login
        Row {
            Text("¿Ya tienes una cuenta? ")
            TextButton(
                onClick = { navController.navigate("login") }
            ) {
                Text("Inicia sesión")
            }
        }
    }
}