package com.stoq.StockWise.shared.domain.entities

/**
 * Entidad de dominio que representa un JWT Token.
 * Esta entidad pertenece al Shared Kernel ya que es utilizada por múltiples bounded contexts.
 */
data class JwtToken(
    val value: String,
    val expiresAt: Long? = null,
    val issuedAt: Long? = null
) {
    /**
     * Verifica si el token está expirado.
     * @return true si el token está expirado, false en caso contrario
     */
    fun isExpired(): Boolean {
        return expiresAt?.let { System.currentTimeMillis() > it } ?: false
    }
    
    /**
     * Verifica si el token es válido (no está vacío y no está expirado).
     * @return true si el token es válido, false en caso contrario
     */
    fun isValid(): Boolean {
        return value.isNotBlank() && !isExpired()
    }
}
