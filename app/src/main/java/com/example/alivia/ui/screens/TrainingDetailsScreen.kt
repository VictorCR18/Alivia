package com.example.alivia.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import com.example.alivia.R
import com.example.alivia.viewmodel.FirebaseViewModel
import com.example.alivia.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
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
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF267A9C))
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) { // Aqui adicionamos um Column para organizar os elementos verticalmente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.intermediario),
                    contentDescription = "Training Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                            )
                        )
                )
            }

            LazyColumn(modifier = Modifier.padding(16.dp)) { // Agora a LazyColumn fica abaixo da imagem
                items(exercises) { exercise ->
                    val isFavoriteLoading = remember { mutableStateOf(false) }
                    val scale = remember { Animatable(1f) }
                    val isClicked = remember { mutableStateOf(false) }

                    LaunchedEffect(isClicked.value) {
                        if (isClicked.value) {
                            scale.animateTo(1.1f, tween(150))
                            scale.animateTo(1f, tween(150))
                            isClicked.value = false
                        }
                    }

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
                                    .size(48.dp * scale.value)
                                    .align(Alignment.TopEnd)
                                    .padding(12.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            isFavoriteLoading.value = true
                                            isClicked.value = true
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

}
