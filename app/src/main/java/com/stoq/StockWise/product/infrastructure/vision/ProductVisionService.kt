package com.stoq.StockWise.product.infrastructure.vision

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.coroutines.tasks.await

/**
 * Servicio que se encarga de analizar una imagen y devolver etiquetas reconocidas
 * usando Google ML Kit.
 */
class ProductVisionService {

    /**
     * Analiza la imagen y devuelve una lista de etiquetas de texto.
     */
    suspend fun analyzeImage(bitmap: Bitmap): List<String> {
        val image = InputImage.fromBitmap(bitmap, 0)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

        return try {
            val labels = labeler.process(image).await()
            labels.map { it.text } // ejemplo: ["leche", "botella", "marca"]
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
