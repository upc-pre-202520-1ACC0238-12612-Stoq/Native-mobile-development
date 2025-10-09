package com.stoq.StockWise.ProductCatalog.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stoq.StockWise.ProductCatalog.presentation.viewmodels.ProductCatalogViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEditModal(
    onDismiss: () -> Unit,
    viewModel: ProductCatalogViewModel = koinViewModel()
) {
    val editState by viewModel.editState.collectAsState()

    if (editState.showEditModal && editState.product != null) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Modal header
                    EditModalHeader(
                        productName = editState.product!!.name,
                        onDismiss = onDismiss
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    // Edit form
                    EditModalForm(
                        editState = editState,
                        onQuantityChange = viewModel::updateEditQuantity,
                        onExpiryDateChange = viewModel::updateEditExpiryDate,
                        onNotesChange = viewModel::updateEditNotes,
                        onTagToggle = viewModel::toggleEditTag,
                        onDuplicate = viewModel::duplicateProduct,
                        onDelete = viewModel::deleteProduct,
                        onDismiss = onDismiss
                    )
                }
            }
        }
    }
}

@Composable
private fun EditModalHeader(
    productName: String,
    onDismiss: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = productName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        )

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = Color(0xFFFF6F00),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun EditModalForm(
    editState: com.stoq.StockWise.ProductCatalog.presentation.viewmodels.ProductEditState,
    onQuantityChange: (String) -> Unit,
    onExpiryDateChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onTagToggle: (String) -> Unit,
    onDuplicate: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // Tags section
        Text(
            text = "Etiqueta",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        TagSelector(
            selectedTags = editState.selectedTags,
            availableTags = listOf("Dulce", "Rellenas", "Saludable", "Integral", "Chispas", "Vainilla"),
            onTagToggle = onTagToggle
        )

        // Quantity field
        OutlinedTextField(
            value = editState.quantity,
            onValueChange = onQuantityChange,
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Expiry date field
        OutlinedTextField(
            value = editState.expiryDate,
            onValueChange = onExpiryDateChange,
            label = { Text("Fecha") },
            placeholder = { Text("DD/MM/AAAA") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        // Notes field
        OutlinedTextField(
            value = editState.notes,
            onValueChange = onNotesChange,
            label = { Text("Notas") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Duplicate button
            OutlinedButton(
                onClick = {
                    onDuplicate()
                    onDismiss()
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFD32F2F)
                ),
                border = BorderStroke(2.dp, Color(0xFFD32F2F))
            ) {
                Text(
                    text = "Duplicar",
                    fontSize = 14.sp
                )
            }

            // Delete button
            Button(
                onClick = {
                    onDelete()
                    onDismiss()
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                )
            ) {
                Text(
                    text = "Eliminar",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun TagSelector(
    selectedTags: Set<String>,
    availableTags: List<String>,
    onTagToggle: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Selected tags
        availableTags.forEach { tag ->
            FilterChip(
                selected = selectedTags.contains(tag),
                onClick = { onTagToggle(tag) },
                label = {
                    Text(
                        text = tag,
                        fontSize = 12.sp
                    )
                },
                enabled = true
            )
        }

        // Add new tag dropdown
        Text(
            text = "+",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier
                .width(60.dp)
                .height(32.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}