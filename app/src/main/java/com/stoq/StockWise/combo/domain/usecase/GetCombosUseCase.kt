package com.stoq.StockWise.combo.domain.usecase

import com.stoq.StockWise.combo.domain.entities.Combo
import com.stoq.StockWise.combo.domain.repository.ComboRepository

/**
 * Caso de uso para obtener todos los combos disponibles.
 */
class GetCombosUseCase(private val repo: ComboRepository) {
    suspend operator fun invoke(): Result<List<Combo>> = repo.getAllCombos()
}
