package com.stoq.StockWise.product.infrastructure.vision

/**
 * Resultado del análisis de visión.
 */
data class VisionResult(
    val labels: List<String>,
    val confidence: List<Float>
)
