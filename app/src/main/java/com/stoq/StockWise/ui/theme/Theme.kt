package com.stoq.StockWise.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun StockWiseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = androidx.compose.material3.lightColorScheme(
            primary = OrangePrimary,
            secondary = BeigeSecondary,
            background = White,
            surface = White,
            onPrimary = White,
            onSecondary = DarkGray,
            onBackground = DarkGray,
            onSurface = DarkGray,
        ),
        typography = Typography,
        content = content
    )
}