package com.stoq.StockWise.inventory.infrastructure.api

import retrofit2.Retrofit

/**
 * Factory para crear instancias de InventoryApiService.
 * Esta clase actúa como un puente entre el módulo shared y el módulo inventory
 * para evitar problemas de dependencias circulares.
 */
object InventoryApiServiceFactory {
    
    /**
     * Crea una instancia del servicio API de inventarios usando el NetworkClient compartido.
     * 
     * @param retrofit Instancia de Retrofit configurada
     * @return InventoryApiService configurado
     */
    inline fun <reified T> create(retrofit: Retrofit): T {
        return retrofit.create(T::class.java)
    }
}

