package com.stoq.StockWise.combo.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.stoq.StockWise.combo.presentation.viewmodel.CombosViewModel
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddComboScreen(navController: NavHostController) {

    val viewModel: CombosViewModel = koinViewModel()
    val state = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadProductsForComboCreation()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Combo", color = TextDark) },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.resetComboCreation()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "Volver", tint = TextDark)
                    }
                },
                actions = {
                    if (!state.isCreatingCombo) {
                        Button(
                            onClick = {
                                viewModel.createNewCombo()
                                if (viewModel.uiState.error == null) {
                                    navController.popBackStack()
                                }
                            },
                            enabled = state.comboName.isNotBlank() &&
                                    state.selectedProducts.isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(ButtonBg)
                        ) {
                            Text("Crear", color = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CombosBg)
            )
        },
        containerColor = CombosBg
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            OutlinedTextField(
                value = state.comboName,
                onValueChange = { viewModel.updateComboName(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Nombre del Combo") },
                placeholder = { Text("Ej: Combo Familiar") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ButtonBg,
                    unfocusedBorderColor = Color.Gray
                )
            )

            if (state.error != null) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = CardDefaults.cardColors(Danger.copy(alpha = 0.1f))
                ) {
                    Text(
                        state.error ?: "",
                        modifier = Modifier.padding(16.dp),
                        color = Danger
                    )
                }
            }

            if (state.isLoadingProducts) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    item {
                        Text(
                            "Seleccionar productos",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = TextDark,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }

                    items(state.availableProducts) { product ->
                        val id = product.id ?: return@items

                        ProductSelectionCard(
                            product = product,
                            isSelected = state.selectedProducts.containsKey(id),
                            quantity = state.selectedProducts[id] ?: 0,
                            onSelectionChanged = {
                                if (it) viewModel.addProductToCombo(id)
                                else viewModel.removeProductFromCombo(id)
                            },
                            onQuantityChanged = { q -> viewModel.updateProductQuantity(id, q) }
                        )
                    }
                }
            }

            if (state.selectedProducts.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(CardBg)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text("Resumen del combo",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = TextDark
                        )

                        Spacer(Modifier.height(8.dp))

                        viewModel.getSelectedProductsWithDetails().forEach { (prod, qty, subtotal) ->
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${prod.name} x$qty", color = TextDark)
                                Text("$${String.format("%.2f", subtotal)}", color = Info)
                            }
                        }

                        Divider(Modifier.padding(vertical = 8.dp))

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total", color = TextDark, fontWeight = FontWeight.Bold)
                            Text("$${String.format("%.2f", viewModel.getTotalPrice())}", color = Info, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if (state.isCreatingCombo) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(Modifier.size(22.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Creando combo…")
                }
            }
        }
    }
}

@Composable
fun ProductSelectionCard(
    product: Product,
    isSelected: Boolean,
    quantity: Int,
    onSelectionChanged: (Boolean) -> Unit,
    onQuantityChanged: (Int) -> Unit
) {
    val name = product.name ?: "Sin nombre"
    val desc = product.description ?: ""
    val price = product.salePrice ?: 0.0

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Info.copy(alpha = 0.08f) else CardBg
        )
    ) {

        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = isSelected,
                onCheckedChange = onSelectionChanged,
                colors = CheckboxDefaults.colors(checkedColor = Info)
            )

            Spacer(Modifier.width(8.dp))

            Column(Modifier.weight(1f)) {
                Text(name, color = TextDark, fontWeight = FontWeight.Medium)
                if (desc.isNotBlank()) {
                    Text(desc, color = Color.Gray)
                }
                Text("$${String.format("%.2f", price)}", color = Info)
            }

            if (isSelected) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(
                        onClick = {
                            val n = quantity - 1
                            if (n >= 1) onQuantityChanged(n)
                            else onSelectionChanged(false)
                        }
                    ) {
                        Icon(Icons.Filled.Remove, contentDescription = "menos", tint = Info)
                    }

                    Text(quantity.toString(), color = TextDark, fontWeight = FontWeight.Bold)

                    IconButton(
                        onClick = { onQuantityChanged(quantity + 1) }
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "más", tint = Info)
                    }
                }
            }
        }
    }
}
