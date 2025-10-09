package com.stoq.StockWise.shared.domain.repositories

import com.stoq.StockWise.shared.domain.entities.JwtToken

/**
 * Contrato de repositorio para el manejo de JWT tokens.
 * Este repositorio pertenece al Shared Kernel ya que es utilizado por múltiples bounded contexts.
 */
interface JwtRepository {
    /**
     * Guarda un JWT token en el almacenamiento local.
     * @param token El token JWT a guardar
     * @return Result<Unit> que indica si la operación fue exitosa
     */
    suspend fun saveToken(token: JwtToken): Result<Unit>
    
    /**
     * Obtiene el JWT token actual del almacenamiento local.
     * @return Result<JwtToken?> que contiene el token o null si no existe
     */
    suspend fun getToken(): Result<JwtToken?>
    
    /**
     * Elimina el JWT token del almacenamiento local.
     * @return Result<Unit> que indica si la operación fue exitosa
     */
    suspend fun clearToken(): Result<Unit>
    
    /**
     * Verifica si existe un token válido en el almacenamiento local.
     * @return Result<Boolean> que indica si existe un token válido
     */
    suspend fun hasValidToken(): Result<Boolean>
}
