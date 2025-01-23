package com.example.alivia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alivia.models.stretchingSessions
import com.example.alivia.viewmodel.SettingsViewModel

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
) {
// Observa os exercícios favoritos persistidos no DataStore
    val favoriteExercises = settingsViewModel.favoriteExercises.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (favoriteExercises.value.isNotEmpty()) {
            // Botão para limpar favoritos
            Text(
                text = "Limpar Favoritos",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable { settingsViewModel.clearFavoriteExercises() }
            )
        }

        LazyColumn {
            if (favoriteExercises.value.isEmpty()) {
                item {
                    Text(
                        text = "Você ainda não tem exercícios favoritos.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(favoriteExercises.value.toList()) { exerciseId ->
                    val exercise = stretchingSessions
                        .flatMap { it.exercises }
                        .find { it.id.toString() == exerciseId }

                    exercise?.let {
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
                                // Verifica se o exercício está na lista de favoritos persistida
                                val isFavorite = favoriteExercises.value.contains(exercise.id.toString())

                                // Ícone de favorito no canto superior direito
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = Color(0xFF267A9C),
                                    modifier = Modifier
                                        .size(48.dp)
                                        .align(Alignment.TopEnd)
                                        .padding(12.dp) // Ajuste do espaçamento
                                        .clickable {
                                            if (isFavorite) {
                                                settingsViewModel.removeFavoriteExercise(exercise.id.toString())
                                            } else {
                                                settingsViewModel.addFavoriteExercise(exercise.id.toString())
                                            }
                                        }
                                )
                                // Layout com Row para alinhar a imagem à esquerda e o texto à direita
                                Row(modifier = Modifier.fillMaxSize()) {
                                    // Imagem do exercício à esquerda
                                    Image(
                                        painter = painterResource(id = exercise.imageRes),
                                        contentDescription = exercise.name,
                                        contentScale = ContentScale.Fit,
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
}
