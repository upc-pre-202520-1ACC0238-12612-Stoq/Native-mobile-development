package com.stoq.StockWise.combo.domain.repository

import com.stoq.StockWise.combo.domain.entities.Combo
import com.stoq.StockWise.combo.domain.entities.ComboItemRequest

/**
 * Interfaz del repositorio del combo siguiendo Domain-Driven Design
 */
interface ComboRepository {

    /**
     * Obtiene todos los combos disponibles.
     */
    suspend fun getAllCombos(): Result<List<Combo>>

    /**
     * Crea un nuevo combo.
     */
    suspend fun createCombo(name: String, items: List<ComboItemRequest>): Result<Combo>
}
