package com.example.alivia.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alivia.models.stretchingSessions
import com.example.alivia.viewmodel.SettingsViewModel

@Composable
fun TrainingDetailsScreen(
    trainingId: String?,
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
) {
    val areAnimationsEnabled = settingsViewModel.areAnimationsEnabled.collectAsState()

    val training =
        stretchingSessions.find { it.id.toString() == trainingId } // Usando a lista atualizada
    training?.let {
        // Imagem de topo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
        ) {
            Image(
                painter = painterResource(id = training.imageRes),
                contentDescription = training.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
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
            Spacer(modifier = Modifier.height(140.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(it.exercises) { exercise ->
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
                            // Ícone de favorito no canto superior direito com animação
                            val scale = remember { Animatable(1f) }

                            if (areAnimationsEnabled.value) {
                                // Lógica para animações habilitadas
                                val isClicked = remember { mutableStateOf(false) }

                                LaunchedEffect(isClicked.value) {
                                    if (isClicked.value) {
                                        scale.animateTo(
                                            targetValue = 1.1f,
                                            animationSpec = tween(150) // Cresce
                                        )
                                        scale.animateTo(
                                            targetValue = 1f,
                                            animationSpec = tween(150) // Retorna ao tamanho original
                                        )
                                        isClicked.value = false // Reseta o clique
                                    }
                                }

                                Icon(
                                    imageVector = if (exercise.isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = Color(0xFF267A9C),
                                    modifier = Modifier
                                        .size(48.dp * scale.value) // Aplica o tamanho animado
                                        .align(Alignment.TopEnd)
                                        .padding(12.dp)
                                        .clickable {
                                            exercise.isFavorite.value = !exercise.isFavorite.value
                                            isClicked.value =
                                                true // Ativa o clique para disparar a animação
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
                                            .size(120.dp)
                                            .padding(8.dp)
                                            .align(Alignment.CenterVertically)
                                    )

                                    // Conteúdo textual à direita
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .weight(1f)
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
                            } else {
                                // Lógica para animações desabilitadas
                                Icon(
                                    imageVector = if (exercise.isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = Color(0xFF267A9C),
                                    modifier = Modifier
                                        .size(48.dp)
                                        .align(Alignment.TopEnd)
                                        .padding(12.dp)
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
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .size(120.dp)
                                            .padding(8.dp)
                                            .align(Alignment.CenterVertically)
                                    )

                                    // Conteúdo textual à direita
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .weight(1f)
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
