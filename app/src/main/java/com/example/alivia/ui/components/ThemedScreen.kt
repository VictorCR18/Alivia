package com.example.alivia.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ThemedScreen() {
    val isDarkTheme = isSystemInDarkTheme() // Detecta o tema do sistema

    Surface(
        color = if (isDarkTheme) Color.Black else Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = if (isDarkTheme) "Modo Escuro" else "Modo Claro",
            color = if (isDarkTheme) Color.White else Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}
