package com.stoq.StockWise.combo.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.entities.Tag
import com.stoq.StockWise.ui.theme.StockWiseTheme
import com.stoq.StockWise.ui.theme.*

/**
 * Pantalla completamente moderna para crear combos siguiendo Material 3 y el estilo StockWise.
 * Diseño limpio con fondo beige, componentes minimalistas y experiencia de usuario fluida.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateComboScreen(
    availableProducts: List<Product> = emptyList(),
    onCreateCombo: (comboName: String, products: List<ComboItem>, total: Double) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: CreateComboViewModel = viewModel()

    // Inicializar productos disponibles
    LaunchedEffect(availableProducts) {
        viewModel.setAvailableProducts(availableProducts)
    }

    val uiState = viewModel.uiState
    val isCreateEnabled = uiState.comboName.isNotBlank() && viewModel.selectedProducts.isNotEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Crear Combo",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = TextDark
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextDark
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (viewModel.validateAndCreateCombo()) {
                                val comboItems = viewModel.getComboItems()
                                val total = viewModel.getTotalPrice()
                                onCreateCombo(uiState.comboName.trim(), comboItems, total)
                                viewModel.reset()
                                onNavigateBack()
                            }
                        },
                        enabled = isCreateEnabled,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (isCreateEnabled) OrangePrimary else TextSecondary
                        )
                    ) {
                        Text(
                            text = "Crear",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5E9D3) // Beige background
                )
            )
        },
        containerColor = Color(0xFFF5E9D3), // Beige background
        modifier = modifier
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Campo minimalista para nombre del combo
            OutlinedTextField(
                value = uiState.comboName,
                onValueChange = viewModel::updateComboName,
                label = {
                    Text(
                        text = "Nombre del Combo",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                },
                placeholder = {
                    Text(
                        text = "Ej: Combo Familiar, Combo Express...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = OrangePrimary,
                    cursorColor = OrangePrimary,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge
            )

            // Mensaje de error si existe
            uiState.errorMessage?.let { error ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    color = Color(0xFFFFEBEE).copy(alpha = 0.95f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = error,
                        color = Danger,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Lista de productos
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header de la sección
                item {
                    Text(
                        text = "Seleccionar Productos",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        ),
                        color = TextDark,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Lista de productos
                items(uiState.availableProducts) { product ->
                    val isSelected = viewModel.isProductSelected(product)
                    val quantity = viewModel.getProductQuantity(product)

                    ModernProductCard(
                        product = product,
                        isSelected = isSelected,
                        quantity = quantity,
                        onSelectionChanged = { viewModel.toggleProductSelection(product) },
                        onIncreaseQuantity = { viewModel.increaseQty(product) },
                        onDecreaseQuantity = { viewModel.decreaseQty(product) }
                    )
                }
            }

            // Resumen del combo (solo si hay productos seleccionados)
            if (viewModel.selectedProducts.isNotEmpty()) {
                ModernComboSummary(
                    selectedProductsDetails = viewModel.getSelectedProductsWithDetails(),
                    totalPrice = viewModel.getTotalPrice(),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                )
            }
        }
    }
}

/**
 * Card moderno para productos con diseño Material 3
 */
@Composable
private fun ModernProductCard(
    product: Product,
    isSelected: Boolean,
    quantity: Int,
    onSelectionChanged: (Boolean) -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    modifier: Modifier = Modifier
) {
    val name = product.name ?: "Sin nombre"
    val description = product.description?.takeIf { it.isNotBlank() }
    val price = product.salePrice ?: 0.0

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFF8E1) else Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 1.dp
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = { onSelectionChanged(!isSelected) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox moderno
            Checkbox(
                checked = isSelected,
                onCheckedChange = onSelectionChanged,
                colors = CheckboxDefaults.colors(
                    checkedColor = OrangePrimary,
                    uncheckedColor = TextSecondary,
                    checkmarkColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Información del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Nombre del producto
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Descripción (opcional)
                description?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Precio
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "$${String.format("%.2f", price)}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Success
                )
            }

            // Controles de cantidad (solo cuando está seleccionado)
            if (isSelected) {
                Spacer(modifier = Modifier.width(16.dp))

                ModernQuantityControls(
                    quantity = quantity,
                    onIncrease = onIncreaseQuantity,
                    onDecrease = onDecreaseQuantity
                )
            }
        }
    }
}

/**
 * Controles modernos de cantidad con botones Material 3
 */
@Composable
private fun ModernQuantityControls(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Botón disminuir
        FilledIconButton(
            onClick = onDecrease,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color(0xFFF0F0F0),
                contentColor = OrangePrimary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = "Disminuir cantidad",
                modifier = Modifier.size(18.dp)
            )
        }

        // Cantidad
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = TextDark,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .widthIn(min = 24.dp)
        )

        // Botón aumentar
        FilledIconButton(
            onClick = onIncrease,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = OrangePrimary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Aumentar cantidad",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/**
 * Resumen moderno del combo con Material 3
 */
@Composable
private fun ModernComboSummary(
    selectedProductsDetails: List<Triple<Product, Int, Double>>,
    totalPrice: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Título del resumen
            Text(
                text = "Resumen del Combo",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = TextDark,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Lista de productos seleccionados
            selectedProductsDetails.forEach { (product, quantity, subtotal) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${product.name ?: "Sin nombre"} × $quantity",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = TextDark,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "$${String.format("%.2f", subtotal)}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Success
                    )
                }
            }

            // Línea divisoria
            Divider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color(0xFFE0E0E0),
                thickness = 1.dp
            )

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextDark
                )

                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = OrangePrimary
                )
            }
        }
    }
}

//region Preview

/**
 * Datos de ejemplo para las previews
 */
private val sampleProducts = listOf(
    Product(
        id = 1,
        name = "Hamburguesa Clásica",
        description = "Deliciosa hamburguesa con carne de res, lechuga, tomate y queso cheddar",
        purchasePrice = 8.50,
        salePrice = 15.99,
        internalNotes = "Ingredientes frescos diariamente",
        categoryId = 1,
        categoryName = "Hamburguesas",
        unitId = 1,
        unitName = "pieza",
        unitAbbreviation = "pz",
        tags = listOf(Tag(1, "Popular"), Tag(2, "Clásico"))
    ),
    Product(
        id = 2,
        name = "Papas Fritas Grandes",
        description = "Porción grande de papas fritas doradas y crujientes",
        purchasePrice = 2.00,
        salePrice = 6.50,
        internalNotes = null,
        categoryId = 2,
        categoryName = "Acompañamientos",
        unitId = 1,
        unitName = "pieza",
        unitAbbreviation = "pz",
        tags = emptyList()
    ),
    Product(
        id = 3,
        name = "Refresco de Cola 500ml",
        description = "Refresco carbonatado de cola en botella de vidrio retornable",
        purchasePrice = 1.20,
        salePrice = 4.99,
        internalNotes = "Botella retornable - bono por devolución",
        categoryId = 3,
        categoryName = "Bebidas",
        unitId = 1,
        unitName = "pieza",
        unitAbbreviation = "pz",
        tags = listOf(Tag(3, "Retornable"))
    ),
    Product(
        id = 4,
        name = "Ensalada César",
        description = "Ensalada fresca con lechuga romana, crutones, queso parmesano y aderezo césar",
        purchasePrice = 4.50,
        salePrice = 12.99,
        internalNotes = "Preparar al momento",
        categoryId = 4,
        categoryName = "Ensaladas",
        unitId = 1,
        unitName = "pieza",
        unitAbbreviation = "pz",
        tags = listOf(Tag(4, "Light"), Tag(5, "Vegetariano"))
    ),
    Product(
        id = 5,
        name = "Helado de Vainilla",
        description = "Bola grande de helado cremoso de vainilla artesanal",
        purchasePrice = 1.80,
        salePrice = 5.99,
        internalNotes = "Hecho con leche fresca",
        categoryId = 5,
        categoryName = "Postres",
        unitId = 1,
        unitName = "pieza",
        unitAbbreviation = "pz",
        tags = listOf(Tag(6, "Artesanal"))
    )
)

/**
 * Preview de la pantalla vacía (sin productos seleccionados)
 */
@Preview(showBackground = true, name = "Pantalla Vacía", heightDp = 600)
@Composable
fun CreateComboScreenEmptyPreview() {
    StockWiseTheme {
        CreateComboScreen(
            availableProducts = sampleProducts,
            onCreateCombo = { _, _, _ -> },
            onNavigateBack = {}
        )
    }
}

/**
 * Preview de la pantalla con productos seleccionados (simulado)
 */
@Preview(showBackground = true, name = "Con Selección", heightDp = 700)
@Composable
fun CreateComboScreenWithSelectionPreview() {
    StockWiseTheme {
        CreateComboScreen(
            availableProducts = sampleProducts,
            onCreateCombo = { name, items, total ->
                println("Crear combo: $name, items: $items, total: $total")
            },
            onNavigateBack = {}
        )
    }
}

/**
 * Preview completa mostrando todos los elementos con resumen
 */
@Preview(showBackground = true, name = "Vista Completa", heightDp = 800)
@Composable
fun CreateComboScreenFullPreview() {
    StockWiseTheme {
        CreateComboScreen(
            availableProducts = sampleProducts,
            onCreateCombo = { name, products, total ->
                println("Combo creado: $name")
                products.forEach { item ->
                    println("- ${item.name} x${item.quantity} = $${item.unitPrice * item.quantity}")
                }
                println("Total: $$total")
            },
            onNavigateBack = { println("Navegando atrás") }
        )
    }
}

//endregion
