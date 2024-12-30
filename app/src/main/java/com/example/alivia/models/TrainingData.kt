package com.example.alivia.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.alivia.R
data class StretchingExercise(
    val id: Int,
    val name: String,
    val description: String,
    val duration: String, // Duração do exercício (em minutos ou segundos)
    val imageRes: Int, // Imagem do exercício (opcional)
    val isFavorite: MutableState<Boolean> = mutableStateOf(false),
    )

data class StretchingSession(
    val id: Int,
    val title: String,
    val description: String,
    val bodyPart: BodyPart, // Parte do corpo a ser trabalhada
    val exercises: List<StretchingExercise>, // Lista de exercícios para o treino
    val isFavorite: MutableState<Boolean> = mutableStateOf(false),
    val isSubscribed: MutableState<Boolean> = mutableStateOf(false),
    val imageRes: Int // Imagem do treino
)

// Enum para representar as diferentes partes do corpo
enum class BodyPart {
    LEGS, ARMS, BACK
}

val stretchingSessions = listOf(
    StretchingSession(
        id = 1,
        title = "Alongamento de Pernas",
        description = "Exercícios para melhorar a flexibilidade e reduzir a tensão nas pernas.",
        bodyPart = BodyPart.LEGS,
        exercises = listOf(
            StretchingExercise(
                id = 1,
                name = "Alongamento de Isquiotibiais",
                description = "Estique os músculos da parte de trás da coxa.",
                duration = "30 segundos",
                imageRes = R.drawable.img3,
                isFavorite = mutableStateOf(false)
                ),
            StretchingExercise(
                id = 2,
                name = "Alongamento de Quadríceps",
                description = "Estique os músculos da parte frontal da coxa.",
                duration = "30 segundos",
                imageRes = R.drawable.img3
            )
        ),
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img3
    ),
    StretchingSession(
        id = 2,
        title = "Alongamento de Braços",
        description = "Exercícios para alongar os músculos dos braços e ombros.",
        bodyPart = BodyPart.ARMS,
        exercises = listOf(
            StretchingExercise(
                id = 1,
                name = "Alongamento de Bíceps",
                description = "Estique os músculos do bíceps.",
                duration = "20 segundos",
                imageRes = R.drawable.img3
            ),
            StretchingExercise(
                id = 2,
                name = "Alongamento de Tríceps",
                description = "Estique os músculos do tríceps.",
                duration = "20 segundos",
                imageRes = R.drawable.img3
            )
        ),
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img3
    ),
    StretchingSession(
        id = 3,
        title = "Alongamento de Costas",
        description = "Exercícios para aliviar a tensão nas costas e melhorar a postura.",
        bodyPart = BodyPart.BACK,
        exercises = listOf(
            StretchingExercise(
                id = 1,
                name = "Alongamento de Coluna",
                description = "Estique a coluna, mantendo a postura correta.",
                duration = "30 segundos",
                imageRes = R.drawable.img3
            ),
            StretchingExercise(
                id = 2,
                name = "Alongamento de Lombar",
                description = "Estique os músculos da região lombar.",
                duration = "30 segundos",
                imageRes = R.drawable.img3
            )
        ),
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img3
    )
)
