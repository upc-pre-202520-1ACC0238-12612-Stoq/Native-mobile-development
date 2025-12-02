package com.stoq.StockWise.Iam.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel
import com.stoq.StockWise.shared.data.local.TermsAndConditions
import com.stoq.StockWise.ui.theme.YellowHighlight
import com.stoq.StockWise.R
import androidx.compose.foundation.text.ClickableText

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    goToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    // Obtener los estados del ViewModel
    val userState by authViewModel.user.collectAsState()
    val registerSuccessState by authViewModel.registerSuccess.collectAsState()
    val errorMessageState by authViewModel.errorMessage.collectAsState()
    val isLoadingState by authViewModel.isLoading.collectAsState()

    // Estado local para controlar aceptación de términos
    var acceptTerms by remember { mutableStateOf(false) }

    // Variables locales
    var passwordVisible by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    // Observar si el registro fue exitoso
    LaunchedEffect(registerSuccessState) {
        if (registerSuccessState == true) {
            onRegisterSuccess()
            authViewModel.resetRegisterSuccess()
        }
    }

    // Limpiar errores al entrar
    LaunchedEffect(Unit) {
        authViewModel.resetErrorMessage()
    }

    // Validación completa del botón
    val isFormValid = userState.email.isNotBlank() &&
                     userState.username.isNotBlank() &&
                     userState.password.isNotBlank() &&
                     acceptTerms

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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = acceptTerms,
                        onCheckedChange = { acceptTerms = it },
                        enabled = !isLoadingState
                    )

                    val annotatedString = buildAnnotatedString {
                        append("Acepto los ")
                        pushStringAnnotation(tag = "TERMS", annotation = "terms")
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append("términos y condiciones")
                        }
                        pop()
                    }

                    ClickableText(
                        text = annotatedString,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        onClick = { offset ->
                            annotatedString.getStringAnnotations(
                                tag = "TERMS",
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let {
                                showTermsDialog = true
                            }
                        }
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
                    onClick = {
                        // Actualizar el ViewModel con el estado local antes de registrar
                        authViewModel.updateAcceptTerms(acceptTerms)
                        authViewModel.register()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isFormValid && !isLoadingState,
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

        // Modal de términos y condiciones
        if (showTermsDialog) {
            TermsAndConditionsModal(
                onDismiss = { showTermsDialog = false }
            )
        }
    }
}

/**
 * Modal para mostrar los términos y condiciones con solo botón de cerrar
 */
@Composable
fun TermsAndConditionsModal(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Términos y Condiciones",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = TermsAndConditions.FULL_TEXT,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2f
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

/**
 * Preview de la pantalla de registro
 * Nota: Este preview muestra la estructura básica sin funcionalidad completa
 */
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(YellowHighlight)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text("LOGO", color = MaterialTheme.colorScheme.primary)
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
                    Text(
                        text = "Regístrate",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Campos de ejemplo
                    OutlinedTextField(
                        value = "usuario@email.com",
                        onValueChange = {},
                        label = { Text("Correo electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = "Juan Pérez",
                        onValueChange = {},
                        label = { Text("Nombres y apellidos") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = "••••••••",
                        onValueChange = {},
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = true,
                            onCheckedChange = {},
                            enabled = false
                        )

                        val annotatedString = buildAnnotatedString {
                            append("Acepto los ")
                            pushStringAnnotation(tag = "TERMS", annotation = "terms")
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                append("términos y condiciones")
                            }
                            pop()
                        }

                        ClickableText(
                            text = annotatedString,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            onClick = {}
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    ) {
                        Text("Registrarse con google")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Registrarse")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text("¿Ya tienes una cuenta? ")
                TextButton(onClick = {}, enabled = false) {
                    Text("Inicia sesión")
                }
            }
        }
    }
}