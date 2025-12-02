package com.stoq.StockWise.inventory.infrastructure.mappers

import com.stoq.StockWise.inventory.domain.entities.Inventory
import com.stoq.StockWise.inventory.infrastructure.dto.InventoryDto

/**
 * Mapper para convertir entre DTOs y entidades de dominio del inventario
 * 
 * Proporciona métodos estáticos para la conversión bidireccional
 * entre la capa de infraestructura y la capa de dominio.
 */
object InventoryMapper {
    
    /**
     * Convierte un DTO a una entidad de dominio
     * 
     * @param dto DTO del inventario
     * @return Entidad de dominio Inventory
     */
    fun fromDto(dto: InventoryDto): Inventory {
        return Inventory(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            userId = dto.userId,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt,
            isActive = dto.isActive
        )
    }
    
    /**
     * Convierte una entidad de dominio a un DTO
     * 
     * @param entity Entidad de dominio Inventory
     * @return DTO del inventario
     */
    fun toDto(entity: Inventory): InventoryDto {
        return InventoryDto(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            userId = entity.userId,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            isActive = entity.isActive
        )
    }
    
    /**
     * Convierte una lista de DTOs a una lista de entidades de dominio
     * 
     * @param dtoList Lista de DTOs del inventario
     * @return Lista de entidades de dominio Inventory
     */
    fun fromDtoList(dtoList: List<InventoryDto>): List<Inventory> {
        return dtoList.map { fromDto(it) }
    }
    
    /**
     * Convierte una lista de entidades de dominio a una lista de DTOs
     * 
     * @param entityList Lista de entidades de dominio Inventory
     * @return Lista de DTOs del inventario
     */
    fun toDtoList(entityList: List<Inventory>): List<InventoryDto> {
        return entityList.map { toDto(it) }
    }
}
