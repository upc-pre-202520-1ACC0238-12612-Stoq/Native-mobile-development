package com.stoq.StockWise.product.presentation.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.usecases.ScanProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ScanUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ScanProductViewModel(
    private val scanProductUseCase: ScanProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState = _uiState.asStateFlow()

    fun scanImage(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.value = ScanUiState(isLoading = true)
            try {
                val product = scanProductUseCase(bitmap)
                _uiState.value = ScanUiState(product = product)
            } catch (e: Exception) {
                _uiState.value = ScanUiState(error = e.message)
            }
        }
    }

    // Reinicia el estado tras un escaneo
    fun resetScan() {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            product = null,
            error = null
        )
    }
}
