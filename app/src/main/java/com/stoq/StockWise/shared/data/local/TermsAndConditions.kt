package com.stoq.StockWise.shared.data.local

/**
 * Constantes para los términos y condiciones de la aplicación STOQ
 */
object TermsAndConditions {

    /**
     * Texto completo de los términos y condiciones
     */
    @JvmField
    val FULL_TEXT = """
1. Aceptación de términos

Al utilizar la aplicación STOQ, usted acepta estar sujeto a estos términos y condiciones.

2. Uso del servicio

• La aplicación está diseñada para la gestión de inventarios
• Debe proporcionar información precisa y actualizada
• Es responsable de mantener la confidencialidad de su cuenta

3. Privacidad

Sus datos serán tratados conforme a nuestra política de privacidad y las leyes aplicables de protección de datos.

4. Responsabilidades

• Usar la aplicación de forma legal y apropiada
• No interferir con el funcionamiento del servicio
• Mantener la seguridad de sus credenciales

5. Limitaciones

El servicio se proporciona "tal como está" sin garantías de ningún tipo.

6. Modificaciones

Nos reservamos el derecho de modificar estos términos en cualquier momento.

Última actualización: Diciembre 2025
    """.trimIndent()

    /**
     * Texto corto para mostrar en el checkbox
     */
    const val SHORT_TEXT = "Acepto los términos y condiciones"
}