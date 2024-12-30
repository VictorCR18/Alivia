package com.example.alivia.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.alivia.models.stretchingSessions

@Composable
fun TrainingDetailsScreen(trainingId: String?, context: Context) {
    val training =
        stretchingSessions.find { it.id.toString() == trainingId } // Usando a lista atualizada
    training?.let {
        // Imagem de topo
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
                    .fillMaxWidth()  // Isso faz a imagem preencher toda a largura da tela
                    .fillMaxSize()
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
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = training.title,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                )
                Text(
                    text = training.description,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {

            Spacer(modifier = Modifier.height(180.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(it.exercises) { exercise ->
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                // Navegar ou realizar ação ao clicar no exercício
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(96.dp)
                        ) {
                            // Ícone de favorito no canto superior direito
                            Icon(
                                imageVector = if (exercise.isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.TopEnd)
                                    .padding(12.dp) // Ajuste do espaçamento
                                    .clickable {
                                        exercise.isFavorite.value = !exercise.isFavorite.value
                                    }
                            )
                            // Layout com Row para alinhar a imagem à esquerda e o texto à direita
                            Row(modifier = Modifier.fillMaxSize()) {
                                // Imagem do exercício à esquerda
                                Image(
                                    painter = painterResource(id = exercise.imageRes),
                                    contentDescription = exercise.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(120.dp) // Ajusta o tamanho da imagem
                                        .padding(8.dp) // Ajusta o espaçamento
                                        .align(Alignment.CenterVertically)
                                )

                                // Conteúdo textual à direita
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .weight(1f) // Ocupa o restante do espaço
                                ) {
                                    Text(
                                        text = exercise.name,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = exercise.duration,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    } ?: run {
        // Exibe uma mensagem se o treino não for encontrado
        Text(
            text = "Treino não encontrado",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
