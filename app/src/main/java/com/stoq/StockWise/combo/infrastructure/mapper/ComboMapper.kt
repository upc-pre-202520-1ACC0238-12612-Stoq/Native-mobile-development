package com.stoq.StockWise.combo.infrastructure.mapper

import com.stoq.StockWise.combo.domain.entities.Combo
import com.stoq.StockWise.combo.domain.entities.ComboItem
import com.stoq.StockWise.combo.domain.entities.ComboItemRequest
import com.stoq.StockWise.combo.infrastructure.dto.ComboDto
import com.stoq.StockWise.combo.infrastructure.dto.ComboItemDto
import com.stoq.StockWise.combo.infrastructure.dto.CreateComboItemRequestDto
import com.stoq.StockWise.combo.infrastructure.dto.CreateComboRequestDto

/**
 * Mapper para convertir entre DTOs y entidades de dominio del combo
 */
object ComboMapper {

    /**
     * Convierte un ComboDto a entidad de dominio Combo
     */
    fun fromDto(dto: ComboDto): Combo {
        return Combo(
            id = dto.id ?: 0,
            name = dto.name ?: "",
            items = dto.items?.map { fromComboItemDto(it) } ?: emptyList()
        )
    }

    /**
     * Convierte una lista de ComboDto a lista de entidades Combo
     */
    fun fromDtoList(dtoList: List<ComboDto>): List<Combo> {
        return dtoList.map { fromDto(it) }
    }

    /**
     * Convierte un ComboItemDto a entidad de dominio ComboItem
     */
    private fun fromComboItemDto(dto: ComboItemDto): ComboItem {
        return ComboItem(
            id = dto.id ?: 0,
            productId = dto.productId ?: 0,
            productName = dto.productName ?: "",
            productDescription = dto.productDescription ?: "",
            productPrice = dto.productPrice ?: 0.0,
            quantity = dto.quantity ?: 0
        )
    }

    /**
     * Convierte una entidad Combo a ComboDto (para respuestas)
     */
    fun toDto(entity: Combo): ComboDto {
        return ComboDto(
            id = entity.id,
            name = entity.name,
            items = entity.items.map { toComboItemDto(it) }
        )
    }

    /**
     * Convierte una entidad ComboItem a ComboItemDto
     */
    private fun toComboItemDto(entity: ComboItem): ComboItemDto {
        return ComboItemDto(
            id = entity.id,
            productId = entity.productId,
            productName = entity.productName,
            productDescription = entity.productDescription,
            productPrice = entity.productPrice,
            quantity = entity.quantity
        )
    }

    /**
     * Convierte ComboItemRequest a CreateComboItemRequestDto
     */
    fun toCreateComboItemRequestDto(request: ComboItemRequest): CreateComboItemRequestDto {
        return CreateComboItemRequestDto(
            productId = request.productId,
            quantity = request.quantity
        )
    }

    /**
     * Crea un CreateComboRequestDto desde nombre e items
     */
    fun createComboRequestDto(name: String, items: List<ComboItemRequest>): CreateComboRequestDto {
        return CreateComboRequestDto(
            name = name,
            items = items.map { toCreateComboItemRequestDto(it) }
        )
    }
}
