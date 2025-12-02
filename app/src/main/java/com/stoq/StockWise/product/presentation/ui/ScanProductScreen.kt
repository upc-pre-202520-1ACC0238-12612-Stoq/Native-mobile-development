package com.stoq.StockWise.product.presentation.ui

import androidx.compose.material.icons.filled.ArrowBack
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stoq.StockWise.product.presentation.viewmodels.ScanProductViewModel
import org.koin.androidx.compose.koinViewModel



data class SavedProduct(
    val label: String,
    val cantidad: Int,
    val ubicacion: String
)


@Composable
fun ConfirmProductSheet(
    tags: List<String>,
    onCancel: () -> Unit,
    onSave: (String, Int, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedLabel by remember { mutableStateOf(tags.firstOrNull() ?: "") }

    var cantidad by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Text(
            "Confirmar Producto",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF3E2723)
        )

        Spacer(Modifier.height(16.dp))

        Text("Etiqueta sugerida", fontSize = 12.sp, color = Color(0xFF5D4037))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF2E4D5))
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 14.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(selectedLabel, color = Color(0xFF3E2723))
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tags.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            selectedLabel = label
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = ubicacion,
            onValueChange = { ubicacion = it },
            label = { Text("Ubicación en el almacén") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(22.dp))

        Row(Modifier.fillMaxWidth()) {

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar")
            }

            Spacer(Modifier.width(10.dp))

            Button(
                onClick = {
                    val cant = cantidad.toIntOrNull() ?: 0
                    onSave(selectedLabel, cant, ubicacion)
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("Guardar", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanProductScreen(
    navController: NavHostController,
    viewModel: ScanProductViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var showSheet by remember { mutableStateOf(false) }

    val savedProducts = remember { mutableStateListOf<SavedProduct>() }

    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf("") }
    var shouldShowSnackbar by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()

    // Cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedImage = bitmap
        bitmap?.let { viewModel.scanImage(it) }
    }


    // Abrir formulario automáticamente cuando tengamos resultados
    LaunchedEffect(uiState.product) {
        if (uiState.product != null) showSheet = true
    }

    LaunchedEffect(shouldShowSnackbar) {
        if (shouldShowSnackbar) {
            snackbarHostState.showSnackbar(snackbarMessage)
            shouldShowSnackbar = false
        }
    }


    if (showSheet && uiState.product?.tags != null) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = Color(0xFFF5E6D3),
            shape = RoundedCornerShape(22.dp)
        ) {

            ConfirmProductSheet(
                tags = uiState.product!!.tags!!.map { it.name ?: "" }.distinct(),
                onCancel = { showSheet = false },
                onSave = { label, cantidad, ubicacion ->

                    // Agregar a lista
                    savedProducts.add(
                        SavedProduct(label, cantidad, ubicacion)
                    )

                    // Preparar snackbar
                    snackbarMessage =
                        "Guardado: $label · Cant: $cantidad · Ubicación: $ubicacion"
                    shouldShowSnackbar = true

                    showSheet = false
                }
            )
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White,
                    snackbarData = data
                )
            }
        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color(0xFF3E2723) // marrón oscuro
                        )
                    }
                },
                title = {
                    Text(
                        "Escanear Producto",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color(0xFF3E2723)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5E6D3)
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { cameraLauncher.launch(null) },
                containerColor = Color(0xFF5D4037),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
            }
        },
        containerColor = Color(0xFFF5E6D3)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {

            Spacer(Modifier.height(16.dp))

            // FOTO
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (capturedImage != null) {
                        Image(
                            bitmap = capturedImage!!.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = Color(0xFF8D6E63),
                            modifier = Modifier.size(70.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(15.dp))


            savedProducts.forEach { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFDEDE8)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // ICONO REDONDO
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFFFCCBC)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "I",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD84315),
                                fontSize = 18.sp
                            )
                        }

                        Spacer(Modifier.width(14.dp))

                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                product.label,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3E2723),
                                fontSize = 16.sp
                            )

                            Text(
                                "Cantidad: ${product.cantidad}   •   Ubicación: ${product.ubicacion}",
                                fontSize = 13.sp,
                                color = Color(0xFF6D4C41)
                            )
                        }
                    }
                }
            }


            if (uiState.isLoading) {
                Spacer(Modifier.height(16.dp))
                CircularProgressIndicator(color = Color(0xFFD84315))
            }

            if (uiState.error != null) {
                Text("Error: ${uiState.error}", color = Color.Red)
            }
        }
    }
}
