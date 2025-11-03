package com.stoq.StockWise.Iam.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    var notificationAlerts by remember { mutableStateOf(true) }
    var autoAlerts by remember { mutableStateOf(true) }
    var multiFormatNotifications by remember { mutableStateOf(false) }
    var specificAlertConfig by remember { mutableStateOf(true) }
    var minorRolesAlerts by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Ajustes",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // Configuraciones de notificaciones
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Configuración de Notificaciones",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                // Opción 1
                NotificationOption(
                    title = "Permiso de notificación de alertas",
                    description = "Alertas automáticas",
                    isChecked = notificationAlerts,
                    onCheckedChange = { notificationAlerts = it }
                )

                // Opción 2
                NotificationOption(
                    title = "Permiso de envío de notificaciones en múltiples formatos",
                    description = "Recibir alertas en diferentes formatos",
                    isChecked = multiFormatNotifications,
                    onCheckedChange = { multiFormatNotifications = it }
                )

                // Opción 3
                NotificationOption(
                    title = "Permiso de configuración específica de alerta",
                    description = "Personalizar tipos de alertas",
                    isChecked = specificAlertConfig,
                    onCheckedChange = { specificAlertConfig = it }
                )

                // Opción 4
                NotificationOption(
                    title = "Roles menores pueden recibir las alertas",
                    description = "Extender notificaciones a roles menores",
                    isChecked = minorRolesAlerts,
                    onCheckedChange = { minorRolesAlerts = it }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de guardar configuración
            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Configuración guardada",
                        Toast.LENGTH_SHORT
                    ).show()
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Cambiar plan")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun NotificationOption(
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}