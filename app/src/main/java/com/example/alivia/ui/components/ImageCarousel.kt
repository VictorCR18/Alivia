package com.example.alivia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun ImageCarousel(imageList: List<Int>, levels: List<String>) {
    val pagerState = rememberPagerState()

    Column {
        // Carrossel
        HorizontalPager(
            count = imageList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(177.dp)
        ) { page ->
            Box(modifier = Modifier.fillMaxWidth()) {
                // Exibir a imagem
                Image(
                    painter = painterResource(id = imageList[page]),
                    contentDescription = "Imagem $page",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )

                // Exibir o texto sobreposto (nível)
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                // Texto sobreposto com o nível (Iniciante, Intermediário, Avançado)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = when (levels[page]) {
                            "beginner" -> "Iniciante"
                            "intermediate" -> "Intermediário"
                            "advanced" -> "Avançado"
                            else -> "Desconhecido"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                }
            }
        }

        // Indicadores (bolinhas)
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
            activeColor = Color(0xFF5DE9E0),
            inactiveColor = Color(0xFF267A9C)
        )
    }
}
