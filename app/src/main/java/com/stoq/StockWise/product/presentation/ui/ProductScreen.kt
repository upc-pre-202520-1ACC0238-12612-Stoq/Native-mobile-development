package com.stoq.StockWise.product.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.entities.Tag
import com.stoq.StockWise.product.presentation.viewmodels.ProductUiState
import com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel
import com.stoq.StockWise.ui.theme.StoqTheme

/**
 * Pantalla principal de productos que replica el diseño de la imagen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5E6D3)) // Color de fondo beige como en la imagen
    ) {
        // Top Bar con logo y menú
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Logo
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Logo",
                        tint = Color(0xFFE65100),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Stock",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100)
                    )
                    Text(
                        text = "Wise",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF5D4037)
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* TODO: Implementar menú */ }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = Color(0xFF5D4037)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFF5E6D3)
            )
        )

        // Título y botón agregar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Agregar Producto",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E2723)
            )
            
            FloatingActionButton(
                onClick = { /* TODO: Implementar agregar producto */ },
                containerColor = Color(0xFFD32F2F),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar producto",
                    tint = Color.White
                )
            }
        }

        // Barra de búsqueda y filtro
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Barra de búsqueda
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(8.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFFBDBDBD),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color(0xFF757575),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    
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

            Spacer(modifier = Modifier.width(12.dp))

            // Botón de filtro
            Button(
                onClick = { /* TODO: Implementar filtro */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier.height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Filtro",
                    tint = Color(0xFF424242),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Filtro",
                    color = Color(0xFF424242),
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
                        color = Color(0xFFE65100)
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
                            onClick = { viewModel.refreshProducts() }
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
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(uiState.filteredProducts) { product ->
                        ProductCard(
                            product = product,
                            onDetailClick = { /* TODO: Implementar detalle */ }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Tarjeta de producto que replica el diseño de la imagen
 */
@Composable
fun ProductCard(
    product: Product,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        border = ButtonDefaults.outlinedButtonBorder
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Información del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name ?: "Sin nombre",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2723)
                )
                Spacer(modifier = Modifier.height(4.dp))
                
                // Descripción y categoría
                val description = product.description ?: ""
                val category = product.categoryName ?: ""
                val displayText = if (category.isNotEmpty() && description.isNotEmpty()) {
                    "$category - $description"
                } else if (category.isNotEmpty()) {
                    category
                } else {
                    description
                }
                
                Text(
                    text = displayText.ifEmpty { "Sin descripción" },
                    fontSize = 14.sp,
                    color = Color(0xFF5D4037)
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                // Stock simulado usando precio de venta
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Stock",
                        tint = Color(0xFF424242),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val stockValue = product.salePrice?.toInt() ?: 0
                    Text(
                        text = "$stockValue Stock",
                        fontSize = 14.sp,
                        color = Color(0xFF424242)
                    )
                }
                
                // Mostrar tags si existen
                product.tags?.takeIf { it.isNotEmpty() }?.let { tags ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tags: ${tags.mapNotNull { it.name }.joinToString(", ")}",
                        fontSize = 12.sp,
                        color = Color(0xFF757575),
                        maxLines = 1
                    )
                }
            }
            
            // Botón de detalle
            Button(
                onClick = onDetailClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6D00)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "+ Detalle",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    StoqTheme {
        ProductCard(
            product = Product(
                id = 2,
                name = "Agua San Luis",
                description = "Agua mineral natural sin gas, 500ml",
                purchasePrice = 0.8,
                salePrice = 1.2,
                internalNotes = "Producto básico, stock mínimo 100 unidades",
                categoryId = 1,
                categoryName = "Bebidas",
                unitId = 1,
                unitName = "Mililitros",
                unitAbbreviation = "ml",
                tags = listOf(
                    Tag(id = 10, name = "Local")
                )
            ),
            onDetailClick = {}
        )
    }
}