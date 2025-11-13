package com.stoq.StockWise.product.domain.usecases

import android.graphics.Bitmap
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.entities.Tag
import com.stoq.StockWise.product.infrastructure.vision.ProductVisionService

/**
 * Caso de uso para escanear productos usando ML Kit.
 */
class ScanProductUseCase(
    private val visionService: ProductVisionService
) {
    suspend operator fun invoke(bitmap: Bitmap): Product {
        val tags = visionService.analyzeImage(bitmap)
        val tagObjects = tags.map { Tag(id = null, name = it) }

        return Product(
            id = null,
            name = tags.firstOrNull(),
            description = null,
            purchasePrice = null,
            salePrice = null,
            internalNotes = null,
            categoryId = null,
            categoryName = null,
            unitId = null,
            unitName = null,
            unitAbbreviation = null,
            tags = tagObjects
        )
    }
}
