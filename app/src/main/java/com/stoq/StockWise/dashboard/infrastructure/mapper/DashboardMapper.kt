package com.stoq.StockWise.dashboard.infrastructure.mapper

import com.stoq.StockWise.dashboard.domain.entities.DashboardProduct
import com.stoq.StockWise.dashboard.infrastructure.dto.InventoryProductDto
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Mapper para convertir entre DTOs y entidades de dominio del dashboard
 */
object DashboardMapper {

    private val isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    private val displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    /**
     * Formatea una fecha ISO a formato dd/MM/yyyy
     */
    private fun formatDate(isoDate: String?): String {
        return try {
            if (isoDate.isNullOrBlank()) {
                "N/A"
            } else {
                val localDateTime = LocalDateTime.parse(isoDate, isoFormatter)
                localDateTime.format(displayFormatter)
            }
        } catch (e: Exception) {
            "N/A"
        }
    }

    /**
     * Convierte un InventoryProductDto a DashboardProduct (entidad de dominio)
     * Para productos recientes del dashboard
     */
    fun fromInventoryProductDto(dto: InventoryProductDto): DashboardProduct {
        return DashboardProduct(
            id = dto.productoId ?: 0,
            name = dto.productoNombre ?: "",
            date = formatDate(dto.fechaEntrada),
            stock = dto.cantidad ?: 0,
            price = dto.precio,
            description = dto.categoriaNombre
        )
    }

    /**
     * Convierte una lista de InventoryProductDto a lista de DashboardProduct
     * Ordenados por fechaEntrada descendente (más recientes primero)
     */
    fun fromInventoryProductDtoList(dtoList: List<InventoryProductDto>): List<DashboardProduct> {
        return dtoList
            .sortedByDescending { dto ->
                try {
                    dto.fechaEntrada?.let { LocalDateTime.parse(it, isoFormatter) }
                } catch (e: Exception) {
                    LocalDateTime.MIN
                }
            }
            .map { fromInventoryProductDto(it) }
    }
}