package com.stoq.StockWise.product.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para la respuesta de la API de productos
 */
data class ProductDto(
    @SerializedName("id")
    val id: Int?,
    
    @SerializedName("name")
    val name: String?,
    
    @SerializedName("description")
    val description: String?,
    
    @SerializedName("purchasePrice")
    val purchasePrice: Double?,
    
    @SerializedName("salePrice")
    val salePrice: Double?,
    
    @SerializedName("internalNotes")
    val internalNotes: String?,
    
    @SerializedName("categoryId")
    val categoryId: Int?,
    
    @SerializedName("categoryName")
    val categoryName: String?,
    
    @SerializedName("unitId")
    val unitId: Int?,
    
    @SerializedName("unitName")
    val unitName: String?,
    
    @SerializedName("unitAbbreviation")
    val unitAbbreviation: String?,
    
    @SerializedName("tags")
    val tags: List<TagDto>?
)

/**
 * DTO para los tags de productos
 */
data class TagDto(
    @SerializedName("id")
    val id: Int?,
    
    @SerializedName("name")
    val name: String?
)