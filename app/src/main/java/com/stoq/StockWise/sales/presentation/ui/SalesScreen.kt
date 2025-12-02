package com.stoq.StockWise.sales.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.stoq.StockWise.sales.domain.entities.SaleItem
import com.stoq.StockWise.sales.presentation.ui.ReceiptModal

import com.stoq.StockWise.sales.presentation.viewmodel.CartItem
import com.stoq.StockWise.sales.presentation.viewmodel.SalesUiState
import com.stoq.StockWise.sales.presentation.viewmodel.SalesViewModel
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.ui.theme.*

import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(
    navController: NavHostController
) {
    val viewModel: SalesViewModel = koinViewModel()
    val state = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Venta", color = TextDark) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CombosBg
                )
            )
        },
        containerColor = CombosBg
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                SearchProductSection(viewModel, state)
            }

            if (state.isLoadingProducts) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                items(state.filteredProducts) { product ->
                    ProductCardForSelection(product = product, viewModel = viewModel, state = state)
                }
            }

            // Mostrar carrito si hay productos en el carrito
            if (state.cart.isNotEmpty()) {
                item {
                    CartSection(viewModel, state)
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }

    // Modal de boleta de venta
    if (state.showReceipt) {
        ReceiptModal(
            viewModel = viewModel,
            cart = state.cart,
            customerName = state.customerName,
            notes = state.notes,
            total = state.cartTotal,
            onDismiss = { viewModel.closeReceipt() }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchProductSection(viewModel: SalesViewModel, state: SalesUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Buscar Producto",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Nombre o descripción...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ButtonBg,
                    unfocusedBorderColor = Color.Gray
                )
            )
        }
    }
}

@Composable
private fun ProductCardForSelection(product: Product, viewModel: SalesViewModel, state: SalesUiState) {
    val isInCart = state.cart.any { it.product.id == product.id }
    val isBeingEdited = state.currentEditingProduct?.id == product.id

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isInCart) Success.copy(alpha = 0.1f) else CardBg
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name ?: "Sin nombre",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    product.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }

                    product.salePrice?.let {
                        Text(
                            text = "$${"%.2f".format(it)}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Success
                        )
                    }
                }

                if (isInCart) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "En carrito",
                        tint = Success,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Panel de cantidad (visible cuando se está editando el producto)
            if (isBeingEdited) {
                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CombosBg.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Agregar al carrito",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = if (state.currentQuantity > 0) state.currentQuantity.toString() else "",
                                onValueChange = {
                                    val q = it.toIntOrNull() ?: 0
                                    viewModel.updateProductQuantity(product.id ?: 0, q)
                                },
                                modifier = Modifier.weight(1f),
                                label = { Text("Cantidad") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ButtonBg,
                                    unfocusedBorderColor = Color.Gray
                                )
                            )

                            Button(
                                onClick = { viewModel.addToCart(product) },
                                enabled = state.currentQuantity > 0,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ButtonBg,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AddShoppingCart,
                                    contentDescription = "Agregar al carrito"
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Agregar")
                            }
                        }
                    }
                }
            }

            // Botón para agregar/editar producto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isInCart) {
                    // Si ya está en el carrito, mostrar opción para cambiar cantidad
                    Button(
                        onClick = { viewModel.startSelectingProduct(product) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonBg,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar cantidad"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cambiar cantidad")
                    }
                } else {
                    // Si no está en el carrito, mostrar opción para agregar
                    Button(
                        onClick = { viewModel.startSelectingProduct(product) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonBg,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Agregar al carrito"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Agregar")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartSection(viewModel: SalesViewModel, state: SalesUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header del carrito
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Carrito de Compras",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Button(
                    onClick = { viewModel.clearCart() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Danger,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Limpiar carrito"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Limpiar")
                }
            }

            // Lista de items en el carrito
            state.cart.forEach { cartItem ->
                CartItemCard(cartItem, viewModel)
            }

            // Formulario de venta
            OutlinedTextField(
                value = state.customerName,
                onValueChange = { viewModel.updateCustomerName(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre del Cliente *") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Cliente") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ButtonBg,
                    unfocusedBorderColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = state.notes,
                onValueChange = { viewModel.updateNotes(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Notas (opcional)") },
                leadingIcon = { Icon(Icons.Filled.Note, contentDescription = "Notas") },
                minLines = 2,
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ButtonBg,
                    unfocusedBorderColor = Color.Gray
                )
            )

            CartTotalCard(viewModel, state)

            SaleButton(viewModel, state)
        }
    }
}

@Composable
private fun CartItemCard(cartItem: CartItem, viewModel: SalesViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CombosBg.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.product.name ?: "Sin nombre",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Text(
                    text = "${cartItem.quantity}x $${"%.2f".format(cartItem.product.salePrice ?: 0.0)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            Text(
                text = "$${"%.2f".format(cartItem.total)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Success
            )

            IconButton(
                onClick = { viewModel.removeFromCart(cartItem.product.id ?: 0) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remover del carrito",
                    tint = Danger
                )
            }
        }
    }
}

@Composable
private fun CartTotalCard(viewModel: SalesViewModel, state: SalesUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Success.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total del carrito:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Text(
                text = viewModel.getTotalFormatted(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Success
            )
        }
    }
}

@Composable
private fun SaleButton(viewModel: SalesViewModel, state: SalesUiState) {
    val isEnabled = viewModel.isFormValid() && !state.isLoading

    Button(
        onClick = { viewModel.openReceipt() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) ButtonBg else Color.Gray,
            contentColor = Color.White
        )
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White
            )
        } else {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Realizar Venta"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Realizar Venta",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

