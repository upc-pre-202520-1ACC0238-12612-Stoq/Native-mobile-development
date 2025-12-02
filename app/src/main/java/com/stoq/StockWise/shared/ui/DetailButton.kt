package com.stoq.StockWise.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Botón de detalle con diseño moderno tipo e-commerce
 *
 * Características:
 * - Ancho completo
 * - Altura 48dp
 * - Bordes redondeados (16dp radius)
 * - Degradado horizontal naranja
 * - Sombra suave (4dp elevation)
 * - Ícono Add + texto "Detalle"
 * - Texto blanco, bold, 16sp
 *
 * @param onClick Acción a ejecutar cuando se presiona el botón
 * @param modifier Modificador opcional para personalización adicional
 */
@Composable
fun DetailButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFFF8A35), // #FF8A35
            Color(0xFFFF6A00)  // #FF6A00
        )
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                brush = gradientBrush,
                shape = MaterialTheme.shapes.extraLarge
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Agregar detalle",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Detalle",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Preview del botón DetailButton para desarrollo
 */
@Preview(showBackground = true)
@Composable
fun DetailButtonPreview() {
    androidx.compose.material3.MaterialTheme {
        DetailButton(
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
