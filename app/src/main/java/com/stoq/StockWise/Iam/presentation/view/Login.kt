package com.stoq.StockWise.Iam.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.ui.theme.YellowHighlight
import com.stoq.StockWise.R

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    goToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // Obtener los estados del ViewModel
    val userState by authViewModel.user.collectAsState()
    val loginSuccessState by authViewModel.loginSuccess.collectAsState()
    val errorMessageState by authViewModel.errorMessage.collectAsState()
    val isLoadingState by authViewModel.isLoading.collectAsState()

    var rememberMe by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Observar si el login fue exitoso
    LaunchedEffect(loginSuccessState) {
        if (loginSuccessState == true) {
            onLoginSuccess()
            authViewModel.resetLoginSuccess()
        }
    }

    // Limpiar errores al entrar
    LaunchedEffect(Unit) {
        authViewModel.resetErrorMessage()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowHighlight)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo "Stock Wise"
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_stockwise),
                contentDescription = "Stock Wise Logo",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "Inicio de sesión",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar error si existe
                errorMessageState?.let { message ->
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                OutlinedTextField(
                    value = userState.email,
                    onValueChange = { authViewModel.updateEmail(it) },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    enabled = !isLoadingState
                )

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = userState.password,
                    onValueChange = { authViewModel.updatePassword(it) },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    enabled = !isLoadingState,
                    trailingIcon = {
                        // Icono de ojo para mostrar/ocultar contraseña
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            enabled = !isLoadingState
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (passwordVisible)
                                        R.drawable.ic_visibility_off
                                    else
                                        R.drawable.ic_visibility
                                ),
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Checkbox "Recuérdame"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        enabled = !isLoadingState
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
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoadingState
                ) {
                    Text("Iniciar con google")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón principal
                Button(
                    onClick = { authViewModel.login() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoadingState,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    if (isLoadingState) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Iniciar sesión")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Enlace a registro
        Row {
            Text("¿No tienes una cuenta? ")
            TextButton(
                onClick = goToRegister,
                enabled = !isLoadingState
            ) {
                Text("Regístrate")
            }
        }
    }
}