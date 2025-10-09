package com.stoq.StockWise.ProductCatalog.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stoq.StockWise.ProductCatalog.presentation.viewmodels.ProductCatalogViewModel
import com.stoq.StockWise.R
import com.stoq.StockWise.ui.theme.YellowHighlight
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductRegisterScreen(
    onBack: () -> Unit,
    onSaveSuccess: () -> Unit,
    viewModel: ProductCatalogViewModel = koinViewModel()
) {
    val formState by viewModel.formState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.errorMessage) {
        // Solo cerrar si hubo un error y ahora está limpio (indicando guardado exitoso)
        // Y no estamos en el estado inicial (form vacío desde el principio)
        if (uiState.errorMessage == null && !formState.name.isBlank() && formState.buyPrice.isBlank()) {
            // Form was cleared after having data, indicating successful save
            onSaveSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowHighlight)
            .padding(15.dp)
            .verticalScroll(scrollState)
    ) {
        // Header
        ProductRegisterHeader()

        Spacer(modifier = Modifier.height(20.dp))

        // Form title
        Text(
            text = "Agregar productos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF6F00),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Error message
        uiState.errorMessage?.let { error ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(12.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Product form
        ProductForm(
            formState = formState,
            onNameChange = viewModel::updateFormName,
            onTagsChange = viewModel::updateFormTags,
            onBuyPriceChange = viewModel::updateFormBuyPrice,
            onSellPriceChange = viewModel::updateFormSellPrice,
            onQuantityChange = viewModel::updateFormQuantity,
            onLotChange = viewModel::updateFormLot,
            onExpiryDateChange = viewModel::updateFormExpiryDate,
            onNoteChange = viewModel::updateFormNote,
            onSave = viewModel::saveProduct,
            onClearError = viewModel::clearError
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Back button
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFFF6F00)
            ),
            border = BorderStroke(1.dp, Color(0xFFFF6F00))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color(0xFFFF6F00),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Volver",
                color = Color(0xFFFF6F00)
            )
        }
    }
}

@Composable
private fun ProductRegisterHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_store),
                contentDescription = "Stock Wise Logo",
                tint = Color(0xFFD32F2F),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Stock Wise",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD32F2F)
            )
        }

        // Menu button
        IconButton(
            onClick = { /* TODO: Open menu */ }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun ProductForm(
    formState: com.stoq.StockWise.ProductCatalog.presentation.viewmodels.ProductFormState,
    onNameChange: (String) -> Unit,
    onTagsChange: (String) -> Unit,
    onBuyPriceChange: (String) -> Unit,
    onSellPriceChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onLotChange: (String) -> Unit,
    onExpiryDateChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onSave: () -> Unit,
    onClearError: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // Name field
        OutlinedTextField(
            value = formState.name,
            onValueChange = {
                onNameChange(it)
                onClearError()
            },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Tags field
        OutlinedTextField(
            value = formState.tags,
            onValueChange = {
                onTagsChange(it)
                onClearError()
            },
            label = { Text("Etiquetas") },
            placeholder = { Text("Ej: Dulce, Rellenas") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Price fields row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = formState.buyPrice,
                onValueChange = {
                    onBuyPriceChange(it)
                    onClearError()
                },
                label = { Text("Precio de compra") },
                placeholder = { Text("$ 00.00") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            OutlinedTextField(
                value = formState.sellPrice,
                onValueChange = {
                    onSellPriceChange(it)
                    onClearError()
                },
                label = { Text("Precio de venta") },
                placeholder = { Text("$ 00.00") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )
        }

        // Quantity field
        OutlinedTextField(
            value = formState.quantity,
            onValueChange = {
                onQuantityChange(it)
                onClearError()
            },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Lot field
        OutlinedTextField(
            value = formState.lot,
            onValueChange = { onLotChange(it) },
            label = { Text("Lote") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Expiry date field
        OutlinedTextField(
            value = formState.expiryDate,
            onValueChange = { onExpiryDateChange(it) },
            label = { Text("Fecha de caducidad") },
            placeholder = { Text("DD/MM/AAAA") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Note field
        OutlinedTextField(
            value = formState.note,
            onValueChange = { onNoteChange(it) },
            label = { Text("Nota") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Save button
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD32F2F)
            )
        ) {
            Text(
                text = "Guardar",
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}