package com.stoq.StockWise.product.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.presentation.viewmodels.ProductUiState
import com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel
import com.stoq.StockWise.ui.theme.StockWiseTheme
import androidx.navigation.NavHostController
import androidx.compose.ui.draw.clip


// Colores cálidos del diseño moderno
private val OrangePrimary = Color(0xFFFF8C42)
private val BeigeBackground = Color(0xFFF5E9D3)
private val BrownLight = Color(0xFFD6C1A5)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel,
    navController: NavHostController,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BeigeBackground,
                        Color.White
                    )
                )
            )
    ) {
        // Top Bar con flecha atrás, logo y menú
        ProductTopBar(
            navController = navController,
            onLogout = onLogout
        )

        // Encabezado con título y botones FAB mini
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Título
            Text(
                text = "Agregar Producto",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E2723)
            )

            // Botones modernos
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Botón agregar manual
                ModernAddButton(
                    onClick = { viewModel.showAddProductModal() },
                    icon = Icons.Default.Add,
                    contentDescription = "Agregar producto manual"
                )

                // Botón agregar por cámara
                ModernAddButton(
                    onClick = { navController.navigate("scan_product") },
                    icon = Icons.Default.CameraAlt,
                    contentDescription = "Agregar producto por cámara",
                    backgroundColor = BrownLight
                )
            }
        }

        // Barra de búsqueda y filtro
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Barra de búsqueda
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        1.dp,
                        BrownLight.copy(alpha = 0.3f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = BrownLight,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    BasicTextField(
                        value = uiState.searchQuery,
                        onValueChange = viewModel::updateSearchQuery,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (uiState.searchQuery.isEmpty()) {
                                Text(
                                    text = "Buscar Productos",
                                    color = Color(0xFF9E9E9E),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }

            // Botón de filtro
            OutlinedButton(
                onClick = { /* TODO: Implementar filtro */ },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = BrownLight.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Filtro",
                    tint = BrownLight,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Filtro",
                    color = BrownLight,
                    fontSize = 14.sp
                )
            }
        }

        // Lista de productos
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = OrangePrimary
                    )
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error: ${uiState.errorMessage}",
                            color = Color(0xFFD32F2F),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.refreshProducts() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OrangePrimary
                            )
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(uiState.filteredProducts) { product ->
                        ProductCardModern(
                            name = product.name ?: "Sin nombre",
                            description = product.description,
                            category = product.categoryName,
                            stock = product.salePrice?.toInt() ?: 0,
                            tags = product.tags?.mapNotNull { it.name } ?: emptyList(),
                            onDetailClick = { viewModel.showProductDetailModal(product) }
                        )
                    }
                }
            }
        }
        
        // Modal de agregar producto
        AddProductModal(
            isVisible = uiState.showAddProductModal,
            isLoading = uiState.isAddingProduct,
            errorMessage = uiState.addProductErrorMessage,
            onDismiss = { viewModel.hideAddProductModal() },
            onAddProduct = { name, description, purchasePrice, salePrice, internalNotes ->
                viewModel.addProduct(name, description, purchasePrice, salePrice, internalNotes)
            }
        )

        // Modal de detalle de producto
        ProductDetailModal(
            product = uiState.selectedProduct,
            isVisible = uiState.showProductDetailModal,
            onDismiss = { viewModel.hideProductDetailModal() },
            onEditProduct = { product -> viewModel.onEditProduct(product) }
        )

        // Modal de editar producto
        EditProductModal(
            product = uiState.editingProduct,
            isVisible = uiState.showEditProductModal,
            isLoading = uiState.isUpdatingProduct,
            errorMessage = uiState.updateProductErrorMessage,
            onDismiss = { viewModel.hideEditProductModal() },
            onSaveProduct = { name, description, purchasePrice, salePrice, internalNotes ->
                uiState.editingProduct?.id?.let { id ->
                    viewModel.updateProduct(id, name, description, purchasePrice, salePrice, internalNotes)
                }
            }
        )
    }
}


/**
 * Botón moderno de agregar producto - circular con sombra suave
 */
@Composable
private fun ModernAddButton(
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    backgroundColor: Color = OrangePrimary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    StockWiseTheme {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BeigeBackground, Color.White)
                    )
                )
                .padding(16.dp)
        ) {
            ProductCardModern(
                name = "Agua San Luis",
                description = "Agua mineral natural sin gas, botella de 500ml perfecta para el día a día",
                category = "Bebidas",
                stock = 150,
                tags = listOf("Local", "Natural", "Sin Gas"),
                onDetailClick = {}
            )
        }
    }
}