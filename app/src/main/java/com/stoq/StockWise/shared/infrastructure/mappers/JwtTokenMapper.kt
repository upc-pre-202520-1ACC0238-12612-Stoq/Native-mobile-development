package com.stoq.StockWise.shared.infrastructure.mappers

import com.stoq.StockWise.shared.domain.entities.JwtToken
import com.stoq.StockWise.shared.infrastructure.dto.JwtTokenDto

/**
 * Mapper para convertir entre JwtToken (entidad de dominio) y JwtTokenDto (DTO).
 * Este mapper pertenece al Shared Kernel ya que es utilizado por múltiples bounded contexts.
 */
object JwtTokenMapper {
    
    /**
     * Convierte un DTO a una entidad de dominio.
     * @param dto El DTO a convertir
     * @return La entidad de dominio correspondiente
     */
    fun fromDto(dto: JwtTokenDto): JwtToken {
        return JwtToken(
            value = dto.token,
            expiresAt = dto.expiresAt,
            issuedAt = dto.issuedAt
        )
    }
    
    /**
     * Convierte una entidad de dominio a un DTO.
     * @param entity La entidad de dominio a convertir
     * @return El DTO correspondiente
     */
    fun toDto(entity: JwtToken): JwtTokenDto {
        return JwtTokenDto(
            token = entity.value,
            expiresAt = entity.expiresAt,
            issuedAt = entity.issuedAt
        )
    }
    
    /**
     * Convierte una lista de DTOs a una lista de entidades de dominio.
     * @param dtoList La lista de DTOs a convertir
     * @return La lista de entidades de dominio correspondientes
     */
    fun fromDtoList(dtoList: List<JwtTokenDto>): List<JwtToken> {
        return dtoList.map { fromDto(it) }
    }
    
    /**
     * Convierte una lista de entidades de dominio a una lista de DTOs.
     * @param entityList La lista de entidades de dominio a convertir
     * @return La lista de DTOs correspondientes
     */
    fun toDtoList(entityList: List<JwtToken>): List<JwtTokenDto> {
        return entityList.map { toDto(it) }
    }
}