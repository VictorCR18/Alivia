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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.alivia.data.remote.ExerciseDto
import com.example.alivia.viewmodel.FirebaseViewModel
import com.example.alivia.viewmodel.RoomViewModel
import com.example.alivia.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.alivia.R

@Composable
fun TrainingDetailsScreen(
    trainingId: String?,
    navController: NavHostController,
    firebaseViewModel: FirebaseViewModel = viewModel(),
    settingsViewModel: SettingsViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    val sessionId = trainingId?.toIntOrNull() ?: -1

    val allExercises by firebaseViewModel.exercisesFlow.collectAsState()
    val exercises = allExercises.filter { it.sessionId == sessionId }

    var isScreenLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(500)
        isScreenLoading = false
    }

    if (isScreenLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF267A9C))
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(exercises) { exercise ->
                val isFavoriteLoading = remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("exerciseDetails/${exercise.id}") },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().height(96.dp)) {
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

                        Icon(
                            imageVector = if (exercise.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
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
                                painter = painterResource(id = exercise.imageRes ?: R.drawable.icon),
                                contentDescription = exercise.name,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(8.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = exercise.name ?: "Sem Nome",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = exercise.duration ?: "",
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

