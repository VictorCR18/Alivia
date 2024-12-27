package com.example.alivia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alivia.R

val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular) // Certifique-se de que o arquivo de fonte esteja corretamente configurado no seu projeto
)

val customTextStyle = TextStyle(
    color = Color(0xFF2BB5CD), // Cor #2BB5CD
    fontFamily = poppinsFontFamily, // Fonte Poppins
    fontSize = 33.sp, // Tamanho de 33px em sp
    fontWeight = FontWeight.Normal, // Peso 400 é normal
    fontStyle = FontStyle.Normal, // Estilo normal
    lineHeight = 1.5.sp // Ou use um valor que se encaixe com o seu design
)

@ExperimentalMaterial3Api
@Composable
fun TopBar(
//    onThemeToggle: () -> Unit,
    onOpenDrawer: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Adiciona o logo à extrema esquerda
                Image(
                    painter = painterResource(id = R.drawable.icon), // Substitua pelo seu recurso de logotipo
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape), // Aplica o formato circular
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "alivia",
                    style = customTextStyle
                )
                Spacer(modifier = Modifier.weight(1f)) // Empurra o ícone de menu para a direita
            }
        },
//        navigationIcon = {
//            IconButton(onClick = onOpenDrawer) { // Abrir o menu ao clicar no ícone
//                Icon(Icons.Default.Menu, contentDescription = "Open Menu")
//            }
//        },
        actions = {
//            IconButton(onClick = onThemeToggle) {
//                Icon(Icons.Default.BrightnessHigh, contentDescription = "Toggle Theme")
//            }
            IconButton(onClick = onOpenDrawer) {
                Icon(Icons.Default.Menu, contentDescription = "Open Menu")
            }
        }
    )
}
