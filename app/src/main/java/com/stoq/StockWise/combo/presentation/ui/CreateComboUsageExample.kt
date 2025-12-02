package com.stoq.StockWise.combo.presentation.ui

import androidx.compose.runtime.Composable
import com.stoq.StockWise.product.domain.entities.Product

/**
 * Uso correcto y limpio de CreateComboScreen sin errores de ViewModel.
 *
 * Este archivo es solo una guía de integración.
 * NO usa ViewModel, NO usa StateFlow → siempre compila.
 */
class CreateComboUsageExample {

    /**
     * Ejemplo correcto de uso en una pantalla padre
     * (MainActivity, Navigation, o cualquier otro Composable).
     */
    @Composable
    fun ExampleUsage(
        availableProducts: List<Product>,
        onNavigateBack: () -> Unit,
        onComboCreated: (comboName: String, products: List<ComboItem>, total: Double) -> Unit
    ) {
        CreateComboScreen(
            availableProducts = availableProducts,
            onCreateCombo = onComboCreated,
            onNavigateBack = onNavigateBack
        )
    }

    /**
     * Ejemplo simplificado de integración con un ViewModel.
     * NO causa errores porque no usa uiState ni StateFlow.
     */
    @Composable
    fun IntegrationWithViewModelSimplified(
        productsFromVM: List<Product>,     // Lo pasas desde tu ViewModel real
        onNavigateBack: () -> Unit,
        onCreateComboFromVM: (String, List<ComboItem>, Double) -> Unit
    ) {
        CreateComboScreen(
            availableProducts = productsFromVM,
            onCreateCombo = onCreateComboFromVM,
            onNavigateBack = onNavigateBack
        )
    }
}
