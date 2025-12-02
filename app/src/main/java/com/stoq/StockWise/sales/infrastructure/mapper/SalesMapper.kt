package com.stoq.StockWise.sales.infrastructure.mapper

import com.stoq.StockWise.sales.domain.entities.SaleRequest
import com.stoq.StockWise.sales.domain.entities.SaleResponse
import com.stoq.StockWise.sales.domain.entities.SaleItem
import com.stoq.StockWise.sales.domain.entities.StockCheck
import com.stoq.StockWise.sales.infrastructure.dto.SaleRequestDto
import com.stoq.StockWise.sales.infrastructure.dto.SaleResponseDto
import com.stoq.StockWise.sales.infrastructure.dto.SaleItemDto
import com.stoq.StockWise.sales.infrastructure.dto.StockCheckDto

/**
 * Mapper para convertir entre DTOs y entidades de dominio de ventas
 */
object SalesMapper {

    /**
     * Convierte SaleRequest a SaleRequestDto
     */
    fun toSaleRequestDto(entity: SaleRequest): SaleRequestDto {
        val itemsDto = entity.items.map { item ->
            SaleItemDto(
                productId = item.product.id,
                quantity = item.quantity,
                subtotal = item.subtotal,
                productName = item.product.name,
                unitPrice = item.product.salePrice
            )
        }

        return SaleRequestDto(
            items = itemsDto,
            customerName = entity.customerName,
            notes = entity.notes
        )
    }

    /**
     * Convierte SaleRequestDto a entidad de dominio SaleRequest
     */
    fun fromSaleRequestDto(dto: SaleRequestDto): SaleRequest {
        val items = dto.items?.mapNotNull { itemDto ->
            // Crear un producto básico desde el DTO
            val product = com.stoq.StockWise.product.domain.entities.Product(
                id = itemDto.productId,
                name = itemDto.productName,
                description = null,
                purchasePrice = null,
                salePrice = itemDto.unitPrice,
                internalNotes = null,
                categoryId = null,
                categoryName = null,
                unitId = null,
                unitName = null,
                unitAbbreviation = null,
                tags = emptyList()
            )

            SaleItem(
                product = product,
                quantity = itemDto.quantity ?: 0,
                subtotal = itemDto.subtotal ?: 0.0
            )
        } ?: emptyList()

        return SaleRequest(
            items = items,
            customerName = dto.customerName ?: "",
            notes = dto.notes
        )
    }

    /**
     * Convierte SaleResponseDto a entidad de dominio SaleResponse
     */
    fun fromSaleResponseDto(dto: SaleResponseDto): SaleResponse {
        return SaleResponse(
            id = dto.id ?: "",
            productId = dto.productId ?: 0,
            productName = dto.productName ?: "",
            quantity = dto.quantity ?: 0,
            unitPrice = dto.unitPrice ?: 0.0,
            total = dto.total ?: 0.0,
            customerName = dto.customerName ?: "",
            notes = dto.notes,
            createdAt = dto.createdAt ?: "",
            stockRemaining = dto.stockRemaining ?: 0
        )
    }

    /**
     * Convierte SaleResponse a SaleResponseDto
     */
    fun toSaleResponseDto(entity: SaleResponse): SaleResponseDto {
        return SaleResponseDto(
            id = entity.id,
            productId = entity.productId,
            productName = entity.productName,
            quantity = entity.quantity,
            unitPrice = entity.unitPrice,
            total = entity.total,
            customerName = entity.customerName,
            notes = entity.notes,
            createdAt = entity.createdAt,
            stockRemaining = entity.stockRemaining
        )
    }

    /**
     * Convierte StockCheckDto a entidad de dominio StockCheck
     */
    fun fromStockCheckDto(dto: StockCheckDto): StockCheck {
        return StockCheck(
            productId = dto.productId ?: 0,
            productName = dto.productName ?: "",
            availableStock = dto.availableStock ?: 0,
            unitPrice = dto.unitPrice ?: 0.0
        )
    }

    /**
     * Convierte StockCheck a StockCheckDto
     */
    fun toStockCheckDto(entity: StockCheck): StockCheckDto {
        return StockCheckDto(
            productId = entity.productId,
            productName = entity.productName,
            availableStock = entity.availableStock,
            unitPrice = entity.unitPrice
        )
    }
}
