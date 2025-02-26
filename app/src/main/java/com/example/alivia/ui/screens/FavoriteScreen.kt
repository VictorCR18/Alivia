package com.example.alivia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.alivia.viewmodel.FirebaseViewModel
import com.example.alivia.viewmodel.RoomViewModel
import com.example.alivia.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import com.example.alivia.R
import kotlinx.coroutines.launch
import androidx.compose.ui.res.painterResource

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    firebaseViewModel: FirebaseViewModel = viewModel(),
    settingsViewModel: SettingsViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    // Simulação de loading (opcional)
    var isScreenLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(1000)
        isScreenLoading = false
    }

    // Observa o fluxo de exercícios do Firebase
    val allExercises by firebaseViewModel.exercisesFlow.collectAsState()
    // Filtra somente os exercícios que estão marcados como favoritos
    val favoriteExercises = allExercises.filter { it.isFavorite == true }

    if (isScreenLoading) {
        // Tela de loading
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF267A9C))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (favoriteExercises.isEmpty()) {
                Text(
                    text = "Você ainda não tem exercícios favoritos.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Text(
                    text = "Exercícios Favoritos",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(favoriteExercises) { exercise ->
                        // Estado local para indicar loading durante a operação de toggle
                        val isFavoriteLoading = remember { mutableStateOf(false) }
                        Card(
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier
                                .clickable {
                                    // Abre os detalhes do exercício
                                    navController.navigate("exerciseDetails/${exercise.id}")
                                }
                                .fillMaxWidth()
                                .height(96.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                if (isFavoriteLoading.value) {
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(Color.Black.copy(alpha = 0.3f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = Color(0xFF267A9C))
                                    }
                                }

                                // Ícone para toggle de favorito
                                Icon(
                                    imageVector = if (exercise.isFavorite == true)
                                        Icons.Default.Favorite
                                    else
                                        Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = Color(0xFF267A9C),
                                    modifier = Modifier
                                        .size(48.dp)
                                        .align(Alignment.TopEnd)
                                        .padding(12.dp)
                                        .clickable {
                                            coroutineScope.launch {
                                                isFavoriteLoading.value = true
                                                firebaseViewModel.toggleFavoriteExercise(exercise)
                                                isFavoriteLoading.value = false
                                            }
                                        }
                                )

                                Row(modifier = Modifier.fillMaxSize()) {
                                    Image(
                                        painter = painterResource(
                                            id = exercise.imageRes ?: R.drawable.icon
                                        ),
                                        contentDescription = exercise.name,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .size(96.dp)
                                            .padding(8.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .weight(1f)
                                    ) {
                                        Text(
                                            text = exercise.name ?: "Sem Nome",
                                            style = MaterialTheme.typography.titleSmall,
                                            softWrap = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = exercise.duration ?: "",
                                            style = MaterialTheme.typography.bodySmall,
                                            softWrap = true,
                                            modifier = Modifier.fillMaxWidth()
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
