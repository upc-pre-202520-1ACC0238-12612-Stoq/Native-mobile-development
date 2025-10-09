package com.stoq.StockWise.Iam.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.stoq.StockWise.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.ui.theme.YellowHighlight
import com.stoq.StockWise.R

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    goToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    // Obtener los estados del ViewModel
    val userState by authViewModel.user.collectAsState()
    val loginSuccessState by authViewModel.loginSuccess.collectAsState()
    val errorMessageState by authViewModel.errorMessage.collectAsState()
    val isLoadingState by authViewModel.isLoading.collectAsState()

    // Variable local
    var acceptTerms by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Observar si el registro fue exitoso
    LaunchedEffect(loginSuccessState) {
        if (loginSuccessState == true) {
            onRegisterSuccess()
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Stock Wise Logo",
                modifier = Modifier
                    .size(120.dp)
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
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "Regístrate",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Mostrar error si existe
                errorMessageState?.let { message ->
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Campo de email
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

                // Campo de nombre completo
                OutlinedTextField(
                    value = userState.username,
                    onValueChange = { authViewModel.updateUsername(it) },
                    label = { Text("Nombres y apellidos") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoadingState
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de contraseña
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

                // Checkbox términos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = acceptTerms,
                        onCheckedChange = { acceptTerms = it },
                        enabled = !isLoadingState
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
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoadingState
                ) {
                    Text("Registrarse con google")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón principal
                Button(
                    onClick = { authViewModel.register() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = acceptTerms && !isLoadingState,
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
                        Text("Registrarse")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Enlace a login
        Row {
            Text("¿Ya tienes una cuenta? ")
            TextButton(
                onClick = goToLogin,
                enabled = !isLoadingState
            ) {
                Text("Inicia sesión")
            }
        }
    }
}