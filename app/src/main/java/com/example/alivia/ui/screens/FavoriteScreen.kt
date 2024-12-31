package com.example.alivia.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alivia.models.stretchingSessions
@Composable
fun FavoritesScreen(navController: NavHostController) {
    // Filtra apenas os exercícios favoritos
    val favoriteExercises = stretchingSessions
        .flatMap { it.exercises } // Combina todas as listas de exercícios em uma única lista
        .filter { it.isFavorite.value } // Filtra apenas os exercícios favoritos

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (favoriteExercises.isNotEmpty()) {
            // Botão para limpar favoritos
            Text(
                text = "Limpar Favoritos",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable {
                        // Atualiza o estado para remover todos dos favoritos
                        stretchingSessions.flatMap { it.exercises }.forEach { it.isFavorite.value = false }
                    }
            )
        }

        LazyColumn {
            if (favoriteExercises.isEmpty()) {
                item {
                    Text(
                        text = "Você ainda não tem exercícios favoritos.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(favoriteExercises) { exercise ->
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate("exerciseDetails/${exercise.id}")
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
    }
}
