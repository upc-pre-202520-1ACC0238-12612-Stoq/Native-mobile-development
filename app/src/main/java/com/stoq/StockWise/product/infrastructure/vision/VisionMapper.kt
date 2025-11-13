package com.stoq.StockWise.product.infrastructure.vision

class VisionMapper {
    fun toVisionResult(rawLabels: List<String>): VisionResult {
        // Por ahora solo simula confianza = 0.85
        return VisionResult(
            labels = rawLabels,
            confidence = List(rawLabels.size) { 0.85f }
        )
    }
}
