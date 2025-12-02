package com.stoq.StockWise.combo.domain.usecase

import com.stoq.StockWise.combo.domain.entities.Combo
import com.stoq.StockWise.combo.domain.entities.ComboItemRequest
import com.stoq.StockWise.combo.domain.repository.ComboRepository

/**
 * Caso de uso para crear un nuevo combo.
 */
class CreateComboUseCase(private val repo: ComboRepository) {
    suspend operator fun invoke(name: String, items: List<ComboItemRequest>): Result<Combo> =
        repo.createCombo(name, items)
}
