package com.stoq.StockWise.shared.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stoq.StockWise.ProductCatalog.presentation.components.ProductCard
import com.stoq.StockWise.ProductCatalog.presentation.view.ProductEditModal
import com.stoq.StockWise.ProductCatalog.presentation.viewmodels.ProductCatalogViewModel
import com.stoq.StockWise.shared.presentation.components.TabHeader
import com.stoq.StockWise.ui.theme.YellowHighlight
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductView(
    onMenuClick: () -> Unit = {},
    onRegisterProduct: () -> Unit = {},
    onProductDetail: (String) -> Unit = {},
    viewModel: ProductCatalogViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val editState by viewModel.editState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(searchQuery) {
        viewModel.updateSearchTerm(searchQuery)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowHighlight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            // Header
            TabHeader(
                title = "Agregar Producto",
                onMenuClick = onMenuClick,
                showAddButton = true,
                onAddClick = { onRegisterProduct() }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Search bar
            SearchAndFilterBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onFilterClick = { /* TODO: Implement filter */ }
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Product list
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    uiState.products.forEach { product ->
                        ProductCard(
                            product = product,
                            onDetailClick = {
                                viewModel.openEditModal(product)
                                onProductDetail(product.id)
                            }
                        )
                    }
                }
            }
        }

        // Edit modal overlay
        ProductEditModal(
            onDismiss = {
                viewModel.closeEditModal()
            }
        )
    }
}

@Composable
private fun SearchAndFilterBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Search box
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Buscar Productos") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Filter button
        OutlinedButton(
            onClick = onFilterClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = Color(0xFFFF6F00),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Filtro",
                fontSize = 14.sp
            )
        }
    }
}