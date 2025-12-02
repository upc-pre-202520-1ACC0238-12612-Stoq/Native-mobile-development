package com.stoq.StockWise.combo.presentation.viewmodel

import com.stoq.StockWise.combo.domain.entities.Combo
import com.stoq.StockWise.product.domain.entities.Product

data class CombosUiState(
    val isLoading: Boolean = false,
    val isCreatingCombo: Boolean = false,
    val isLoadingProducts: Boolean = false,

    val combos: List<Combo> = emptyList(),
    val filteredCombos: List<Combo> = emptyList(),

    val availableProducts: List<Product> = emptyList(),

    val comboName: String = "",
    val selectedProducts: Map<Int, Int> = emptyMap(),

    val searchQuery: String = "",
    val error: String? = null
)
