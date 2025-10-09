package com.stoq.StockWise.product.infrastructure.mappers

import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.entities.Tag
import com.stoq.StockWise.product.infrastructure.dto.ProductDto
import com.stoq.StockWise.product.infrastructure.dto.TagDto

/**
 * Mapper para convertir entre DTOs y entidades de dominio
 */
object ProductMapper {
    
    /**
     * Convierte un ProductDto a Product (entidad de dominio)
     */
    fun fromDto(dto: ProductDto): Product {
        return Product(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            purchasePrice = dto.purchasePrice,
            salePrice = dto.salePrice,
            internalNotes = dto.internalNotes,
            categoryId = dto.categoryId,
            categoryName = dto.categoryName,
            unitId = dto.unitId,
            unitName = dto.unitName,
            unitAbbreviation = dto.unitAbbreviation,
            tags = dto.tags?.map { fromTagDto(it) }
        )
    }
    
    /**
     * Convierte una Product (entidad de dominio) a ProductDto
     */
    fun toDto(product: Product): ProductDto {
        return ProductDto(
            id = product.id,
            name = product.name,
            description = product.description,
            purchasePrice = product.purchasePrice,
            salePrice = product.salePrice,
            internalNotes = product.internalNotes,
            categoryId = product.categoryId,
            categoryName = product.categoryName,
            unitId = product.unitId,
            unitName = product.unitName,
            unitAbbreviation = product.unitAbbreviation,
            tags = product.tags?.map { toTagDto(it) }
        )
    }
    
    /**
     * Convierte un TagDto a Tag (entidad de dominio)
     */
    private fun fromTagDto(dto: TagDto): Tag {
        return Tag(
            id = dto.id,
            name = dto.name
        )
    }
    
    /**
     * Convierte una Tag (entidad de dominio) a TagDto
     */
    private fun toTagDto(tag: Tag): TagDto {
        return TagDto(
            id = tag.id,
            name = tag.name
        )
    }
    
    /**
     * Convierte una lista de ProductDto a lista de Product
     */
    fun fromDtoList(dtoList: List<ProductDto>): List<Product> {
        return dtoList.map { fromDto(it) }
    }
}