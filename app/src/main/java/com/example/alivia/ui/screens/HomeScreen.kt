package com.example.alivia.ui.screens

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavHostController
import com.example.alivia.R
import com.example.alivia.models.stretchingSessions
import com.example.alivia.ui.components.ImageCarousel

@Composable
fun HomeScreen(navController: NavHostController, context: Context) {
    Column {
        // Usar o componente do carrossel
        val images = listOf(
            R.drawable.img3,
            R.drawable.img3,
            R.drawable.img3
        )
        // Lista com os níveis correspondentes às imagens
        val levels = listOf("beginner", "intermediate", "advanced")

        ImageCarousel(imageList = images, levels = levels)

// Seção principal com a lista de todos os eventos
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(stretchingSessions) { training ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("trainingDetails/${training.id}")
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        // Imagem do evento
                        Image(
                            painter = painterResource(id = training.imageRes),
                            contentDescription = training.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
//                                .clip(CardDefaults.shape)
                        )

                        // Gradiente de fundo
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

                        // Conteúdo textual sobreposto
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = training.title,
                                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                            )
                            Text(
                                text = training.description,
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(8.dp))
    }
}

