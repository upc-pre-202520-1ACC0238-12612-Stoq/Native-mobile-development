package com.stoq.StockWise.combo.infrastructure.repository

import com.stoq.StockWise.combo.domain.entities.Combo
import com.stoq.StockWise.combo.domain.entities.ComboItemRequest
import com.stoq.StockWise.combo.domain.repository.ComboRepository
import com.stoq.StockWise.combo.infrastructure.api.ComboApiService
import com.stoq.StockWise.combo.infrastructure.mapper.ComboMapper

/**
 * Implementación del repositorio del combo que consume la API REST
 */
class ComboRepositoryImpl(
    private val apiService: ComboApiService
) : ComboRepository {

    override suspend fun getAllCombos(): Result<List<Combo>> {
        return try {
            println("ComboRepositoryImpl: LLAMANDO GET /api/v1/combos ...")
            val response = apiService.getCombos()

            if (response.isSuccessful) {
                val comboDtos = response.body() ?: emptyList()
                val combos = ComboMapper.fromDtoList(comboDtos)

                println("ComboRepositoryImpl: Obtenidos ${combos.size} combos")
                Result.success(combos)
            } else {
                val errorMsg = "Error al obtener combos: " +
                    "Combos(${response.code()}: ${response.message()})"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            println("ComboRepositoryImpl: Error = ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun createCombo(name: String, items: List<ComboItemRequest>): Result<Combo> {
        return try {
            println("ComboRepositoryImpl: LLAMANDO POST /api/v1/combos ...")

            val requestDto = ComboMapper.createComboRequestDto(name, items)
            val response = apiService.createCombo(requestDto)

            if (response.isSuccessful) {
                val comboDto = response.body()
                if (comboDto != null) {
                    val combo = ComboMapper.fromDto(comboDto)
                    println("ComboRepositoryImpl: Combo creado exitosamente: ${combo.name}")
                    Result.success(combo)
                } else {
                    Result.failure(Exception("Respuesta vacía del servidor"))
                }
            } else {
                val errorMsg = "Error al crear combo: " +
                    "Combos(${response.code()}: ${response.message()})"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            println("ComboRepositoryImpl: Error = ${e.message}")
            Result.failure(e)
        }
    }
}
