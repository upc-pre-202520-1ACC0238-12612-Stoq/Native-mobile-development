package com.stoq.StockWise.dashboard.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para un producto en el inventario (de /api/v1/inventory)
 */
data class InventoryProductDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("productoId")
    val productoId: Int?,

    @SerializedName("productoNombre")
    val productoNombre: String?,

    @SerializedName("categoriaNombre")
    val categoriaNombre: String?,

    @SerializedName("unidadNombre")
    val unidadNombre: String?,

    @SerializedName("unidadAbreviacion")
    val unidadAbreviacion: String?,

    @SerializedName("fechaEntrada")
    val fechaEntrada: String?,

    @SerializedName("cantidad")
    val cantidad: Int?,

    @SerializedName("precio")
    val precio: Double?,

    @SerializedName("stockMinimo")
    val stockMinimo: Int?,

    @SerializedName("total")
    val total: Double?,

    @SerializedName("stockBajo")
    val stockBajo: Boolean?
)

/**
 * DTO para un lote en el inventario
 */
data class InventoryLotDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("productoId")
    val productoId: Int?,

    @SerializedName("productoNombre")
    val productoNombre: String?,

    @SerializedName("unidadNombre")
    val unidadNombre: String?,

    @SerializedName("unidadAbreviacion")
    val unidadAbreviacion: String?,

    @SerializedName("proveedor")
    val proveedor: String?,

    @SerializedName("fechaEntrada")
    val fechaEntrada: String?,

    @SerializedName("cantidad")
    val cantidad: Int?,

    @SerializedName("precio")
    val precio: Double?,

    @SerializedName("total")
    val total: Double?
)

/**
 * DTO wrapper para la respuesta completa de la API de inventario
 * Contiene dos listas: productos y lotes
 */
data class InventoryResponseDto(
    @SerializedName("productos")
    val productos: List<InventoryProductDto>?,

    @SerializedName("lotes")
    val lotes: List<InventoryLotDto>?
)