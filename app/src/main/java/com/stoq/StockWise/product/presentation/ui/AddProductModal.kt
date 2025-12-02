package com.stoq.StockWise.product.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.ui.theme.StockWiseTheme
import androidx.compose.material.icons.filled.Error


// Colores de la paleta
private val OrangePrimary = Color(0xFFFF8A00)
private val BrownTitle = Color(0xFF5D4037)
private val BeigeBackground = Color(0xFFF5E9D3)
private val GrayLight = Color(0xFFBDBDBD)
private val GrayText = Color(0xFF757575)

/**
 * Modal moderno para agregar un nuevo producto
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductModal(
    isVisible: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onDismiss: () -> Unit,
    onAddProduct: (name: String, description: String?, purchasePrice: Double?, salePrice: Double?, internalNotes: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isVisible) return

    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var salePrice by remember { mutableStateOf("") }
    var internalNotes by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp,
                pressedElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header del modal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Agregar Producto",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrownTitle
                    )

                    IconButton(
                        onClick = onDismiss,
                        enabled = !isLoading,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = BeigeBackground.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = BrownTitle,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Formulario
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Sección Información Básica
                    SectionCard(title = "Información Básica") {
                        // Campo Nombre (obligatorio)
                        ModernTextField(
                            value = productName,
                            onValueChange = { productName = it },
                            label = "Nombre del producto *",
                            placeholder = "Ej: Laptop Dell Inspiron",
                            enabled = !isLoading,
                            isError = productName.isBlank() && productName.isNotEmpty(),
                            errorMessage = if (productName.isBlank() && productName.isNotEmpty())
                                "El nombre es obligatorio" else null
                        )

                        // Campo Descripción
                        ModernTextField(
                            value = productDescription,
                            onValueChange = { productDescription = it },
                            label = "Descripción",
                            placeholder = "Descripción del producto (opcional)",
                            enabled = !isLoading,
                            minLines = 3,
                            maxLines = 4
                        )
                    }

                    // Sección Precios
                    SectionCard(title = "Precios") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Precio de compra
                            ModernTextField(
                                value = purchasePrice,
                                onValueChange = {
                                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                        purchasePrice = it
                                    }
                                },
                                label = "Precio de compra",
                                placeholder = "0.00",
                                enabled = !isLoading,
                                keyboardType = KeyboardType.Decimal,
                                prefix = "$",
                                modifier = Modifier.weight(1f)
                            )

                            // Precio de venta
                            ModernTextField(
                                value = salePrice,
                                onValueChange = {
                                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                        salePrice = it
                                    }
                                },
                                label = "Precio de venta",
                                placeholder = "0.00",
                                enabled = !isLoading,
                                keyboardType = KeyboardType.Decimal,
                                prefix = "$",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Sección Notas Internas
                    SectionCard(title = "Notas Internas") {
                        ModernTextField(
                            value = internalNotes,
                            onValueChange = { internalNotes = it },
                            label = "Notas adicionales",
                            placeholder = "Información adicional sobre el producto (opcional)",
                            enabled = !isLoading,
                            minLines = 3,
                            maxLines = 5
                        )
                    }

                    // Mensaje de error
                    if (errorMessage != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFEBEE).copy(alpha = 0.8f)
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Text(
                                text = errorMessage,
                                color = Color(0xFFD32F2F),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Botones
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Botón Cancelar
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .border(
                                    width = 1.dp,
                                    color = GrayLight,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            enabled = !isLoading,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = GrayText
                            )
                        ) {
                            Text(
                                "Cancelar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }


                        // Botón Agregar
                        Button(
                            onClick = {
                                if (productName.isNotBlank()) {
                                    val purchasePriceValue = purchasePrice.toDoubleOrNull()
                                    val salePriceValue = salePrice.toDoubleOrNull()

                                    onAddProduct(
                                        productName.trim(),
                                        productDescription.takeIf { it.isNotBlank() },
                                        purchasePriceValue,
                                        salePriceValue,
                                        internalNotes.takeIf { it.isNotBlank() }
                                    )
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            enabled = !isLoading && productName.isNotBlank(),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                OrangePrimary,
                                                OrangePrimary.copy(alpha = 0.8f)
                                            )
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        "Agregar",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Componente para secciones del formulario
 */
@Composable
private fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = BrownTitle
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = BeigeBackground.copy(alpha = 0.3f)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                content()
            }
        }
    }
}

/**
 * Campo de texto moderno con diseño Material You
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    prefix: String? = null,
    minLines: Int = 1,
    maxLines: Int = 1,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Etiqueta personalizada
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (isError) Color(0xFFD32F2F) else BrownTitle,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Campo de texto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = when {
                        isError -> Color(0xFFD32F2F).copy(alpha = 0.5f)
                        !enabled -> GrayLight.copy(alpha = 0.3f)
                        else -> GrayLight.copy(alpha = 0.4f)
                    },
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = if (enabled) Color.White else BeigeBackground.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            if (prefix != null) {
                Text(
                    text = prefix,
                    fontSize = 16.sp,
                    color = GrayText,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
            }

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    Text(
                        text = placeholder,
                        fontSize = 16.sp,
                        color = GrayText.copy(alpha = 0.6f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .then(if (prefix != null) Modifier.padding(start = 32.dp) else Modifier),
                enabled = enabled,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                ),
                minLines = minLines,
                maxLines = maxLines,
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp,
                    color = if (enabled) Color.Black else GrayText
                )
            )
        }

        // Mensaje de error
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                fontSize = 12.sp,
                color = Color(0xFFD32F2F),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductModalPreview() {
    StockWiseTheme {
        AddProductModal(
            isVisible = true,
            isLoading = false,
            errorMessage = null,
            onDismiss = {},
            onAddProduct = { _, _, _, _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductModalLoadingPreview() {
    StockWiseTheme {
        AddProductModal(
            isVisible = true,
            isLoading = true,
            errorMessage = null,
            onDismiss = {},
            onAddProduct = { _, _, _, _, _ -> }
        )
    }
}

/**
 * Modal reutilizable para agregar o editar un producto
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductModal(
    product: Product? = null,
    isVisible: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onDismiss: () -> Unit,
    onSaveProduct: (name: String, description: String?, purchasePrice: Double?, salePrice: Double?, internalNotes: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isVisible) return

    val isEditing = product != null

    // Inicializar campos con datos del producto si estamos editando
    var productName by remember(product?.id) { mutableStateOf(product?.name ?: "") }
    var productDescription by remember(product?.id) { mutableStateOf(product?.description ?: "") }
    var purchasePrice by remember(product?.id) { mutableStateOf(product?.purchasePrice?.toString() ?: "") }
    var salePrice by remember(product?.id) { mutableStateOf(product?.salePrice?.toString() ?: "") }
    var internalNotes by remember(product?.id) { mutableStateOf(product?.internalNotes ?: "") }

    // Validación adicional para edición: venta >= compra (advertencia, no bloqueo)
    val purchasePriceValue = purchasePrice.toDoubleOrNull()
    val salePriceValue = salePrice.toDoubleOrNull()
    val hasPriceWarning = purchasePriceValue != null && salePriceValue != null && salePriceValue < purchasePriceValue

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp,
                pressedElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header del modal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEditing) "Editar Producto" else "Agregar Producto",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrownTitle
                    )

                    IconButton(
                        onClick = onDismiss,
                        enabled = !isLoading,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = BeigeBackground.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = BrownTitle,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Formulario
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Sección Información Básica
                    SectionCard(title = "Información Básica") {
                        // Campo Nombre (obligatorio)
                        ModernTextField(
                            value = productName,
                            onValueChange = { productName = it },
                            label = "Nombre del producto *",
                            placeholder = "Ej: Laptop Dell Inspiron",
                            enabled = !isLoading,
                            isError = productName.isBlank() && productName.isNotEmpty(),
                            errorMessage = if (productName.isBlank() && productName.isNotEmpty())
                                "El nombre es obligatorio" else null
                        )

                        // Campo Descripción
                        ModernTextField(
                            value = productDescription,
                            onValueChange = { productDescription = it },
                            label = "Descripción",
                            placeholder = "Descripción del producto (opcional)",
                            enabled = !isLoading,
                            minLines = 3,
                            maxLines = 4
                        )
                    }

                    // Sección Precios
                    SectionCard(title = "Precios") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Precio de compra
                            ModernTextField(
                                value = purchasePrice,
                                onValueChange = {
                                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                        purchasePrice = it
                                    }
                                },
                                label = "Precio de compra",
                                placeholder = "0.00",
                                enabled = !isLoading,
                                keyboardType = KeyboardType.Decimal,
                                prefix = "$",
                                modifier = Modifier.weight(1f)
                            )

                            // Precio de venta
                            ModernTextField(
                                value = salePrice,
                                onValueChange = {
                                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                        salePrice = it
                                    }
                                },
                                label = "Precio de venta",
                                placeholder = "0.00",
                                enabled = !isLoading,
                                keyboardType = KeyboardType.Decimal,
                                prefix = "$",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Advertencia si venta < compra
                        if (hasPriceWarning) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFF3E0).copy(alpha = 0.8f)
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 1.dp
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Error,
                                        contentDescription = "Advertencia",
                                        tint = Color(0xFFFF9800)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "El precio de venta es menor al precio de compra",
                                        color = Color(0xFFEF6C00),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    // Sección Notas Internas
                    SectionCard(title = "Notas Internas") {
                        ModernTextField(
                            value = internalNotes,
                            onValueChange = { internalNotes = it },
                            label = "Notas adicionales",
                            placeholder = "Información adicional sobre el producto (opcional)",
                            enabled = !isLoading,
                            minLines = 3,
                            maxLines = 5
                        )
                    }

                    // Mensaje de error
                    if (errorMessage != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFEBEE).copy(alpha = 0.8f)
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Text(
                                text = errorMessage,
                                color = Color(0xFFD32F2F),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Botones
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Botón Cancelar
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .border(
                                    width = 1.dp,
                                    color = GrayLight,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            enabled = !isLoading,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = GrayText
                            )
                        ) {
                            Text(
                                "Cancelar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Botón Guardar/Agregar
                        Button(
                            onClick = {
                                if (productName.isNotBlank()) {
                                    val purchasePriceValue = purchasePrice.toDoubleOrNull()
                                    val salePriceValue = salePrice.toDoubleOrNull()

                                    onSaveProduct(
                                        productName.trim(),
                                        productDescription.takeIf { it.isNotBlank() },
                                        purchasePriceValue,
                                        salePriceValue,
                                        internalNotes.takeIf { it.isNotBlank() }
                                    )
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            enabled = !isLoading && productName.isNotBlank(),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                OrangePrimary,
                                                OrangePrimary.copy(alpha = 0.8f)
                                            )
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        if (isEditing) "Guardar Cambios" else "Agregar",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductModalErrorPreview() {
    StockWiseTheme {
        AddProductModal(
            isVisible = true,
            isLoading = false,
            errorMessage = "Error al conectar con el servidor. Intenta de nuevo.",
            onDismiss = {},
            onAddProduct = { _, _, _, _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProductModalPreview() {
    val sampleProduct = Product(
        id = 1,
        name = "Agua Mineral San Luis",
        description = "Agua mineral natural premium",
        purchasePrice = 12.50,
        salePrice = 18.00,
        internalNotes = "Proveedor: Distribuidora ABC",
        categoryId = 1,
        categoryName = "Bebidas",
        unitId = 1,
        unitName = "Botella",
        unitAbbreviation = "btl",
        tags = listOf()
    )

    StockWiseTheme {
        EditProductModal(
            product = sampleProduct,
            isVisible = true,
            isLoading = false,
            errorMessage = null,
            onDismiss = {},
            onSaveProduct = { _, _, _, _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProductModalLoadingPreview() {
    val sampleProduct = Product(
        id = 1,
        name = "Agua Mineral San Luis",
        description = "Agua mineral natural premium",
        purchasePrice = 12.50,
        salePrice = 18.00,
        internalNotes = "Proveedor: Distribuidora ABC",
        categoryId = 1,
        categoryName = "Bebidas",
        unitId = 1,
        unitName = "Botella",
        unitAbbreviation = "btl",
        tags = listOf()
    )

    StockWiseTheme {
        EditProductModal(
            product = sampleProduct,
            isVisible = true,
            isLoading = true,
            errorMessage = null,
            onDismiss = {},
            onSaveProduct = { _, _, _, _, _ -> }
        )
    }
}
