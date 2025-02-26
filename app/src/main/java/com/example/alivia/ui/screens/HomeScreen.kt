package com.example.alivia.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import com.example.alivia.ui.components.ImageCarousel
import com.example.alivia.viewmodel.FirebaseViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    context: Context,
    firebaseViewModel: FirebaseViewModel = viewModel()
) {
    Column {
        val images = listOf(
            R.drawable.iniciante,
            R.drawable.intermediario,
            R.drawable.avancado
        )
        val levels = listOf("beginner", "intermediate", "advanced")
        ImageCarousel(imageList = images, levels = levels)

        val sessionsState = firebaseViewModel.sessionsFlow.collectAsState()
        val sessions = sessionsState.value

        LaunchedEffect(sessions) {
            println("Fetched sessions count: ${sessions.size}")
        }

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            if (sessions.isEmpty()) {
                item {
                    Text(
                        text = "Nenhuma sessão encontrada no banco.",
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        color = Color.Gray
                    )
                }
            } else {
                items(sessions) { session ->
                    val imageRes = session.imageRes ?: R.drawable.icon
                    val titleText = session.title ?: "Sem Título"
                    val descriptionText = session.description ?: "Sem Descrição"

                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier
                            .clickable {
                                navController.navigate("trainingDetails/${session.id}")
                            }
                            .fillMaxWidth()
                            .height(160.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = titleText,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
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
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = titleText,
                                    color = Color.White
                                )
                                Text(
                                    text = descriptionText,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
