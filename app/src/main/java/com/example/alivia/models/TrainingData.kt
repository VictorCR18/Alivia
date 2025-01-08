package com.example.alivia.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.alivia.R

data class StretchingExercise(
    val id: Int,
    val name: String,
    val description: String,
    val duration: String, // Duração do exercício
    val imageRes: Int, // Imagem do exercício
    val videoFileName: String, // Nome do arquivo de vídeo no diretório 'raw'
    val isFavorite: MutableState<Boolean> = mutableStateOf(false),
)

data class StretchingSession(
    val id: Int,
    val title: String,
    val description: String,
    val bodyPart: BodyPart, // Parte do corpo a ser trabalhada
    val exercises: List<StretchingExercise>, // Lista de exercícios para o treino
    val isFavorite: MutableState<Boolean> = mutableStateOf(false),
    val imageRes: Int // Imagem do treino
)

// Enum para representar as diferentes partes do corpo
enum class BodyPart {
    LEGS, ARMS, BACK, FULL_BODY
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
                imageRes = R.drawable.alongamento_de_isquiotibiais,
                videoFileName = "videoplayback"
            ),
            StretchingExercise(
                id = 2,
                name = "Alongamento de Quadríceps",
                description = "Estique os músculos da parte frontal da coxa.",
                duration = "30 segundos",
                imageRes = R.drawable.alongamento_de_quadriceps,
                videoFileName = "videoplayback"
            ),
            StretchingExercise(
                id = 3,
                name = "Alongamento de Panturrilha",
                description = "Estique os músculos da panturrilha.",
                duration = "20 segundos",
                imageRes = R.drawable.alongamento_de_panturrilha,
                videoFileName = "videoplayback"
            )
        ),
        isFavorite = mutableStateOf(false),
        imageRes = R.drawable.alongamento_pernas
    ),
    StretchingSession(
        id = 2,
        title = "Alongamento de Braços",
        description = "Exercícios para alongar os músculos dos braços e ombros.",
        bodyPart = BodyPart.ARMS,
        exercises = listOf(
            StretchingExercise(
                id = 4,
                name = "Alongamento de Bíceps",
                description = "Estique os músculos do bíceps.",
                duration = "20 segundos",
                imageRes = R.drawable.alongamento_de_biceps,
                videoFileName = "videoplayback"
            ),
            StretchingExercise(
                id = 5,
                name = "Alongamento de Tríceps",
                description = "Estique os músculos do tríceps.",
                duration = "20 segundos",
                imageRes = R.drawable.alongamento_de_triceps,
                videoFileName = "videoplayback"
            ),
            StretchingExercise(
                id = 6,
                name = "Alongamento de Ombros",
                description = "Estique os músculos dos ombros.",
                duration = "20 segundos",
                imageRes = R.drawable.alongamento_de_ombro,
                videoFileName = "videoplayback"
            )
        ),
        isFavorite = mutableStateOf(false),
        imageRes = R.drawable.alongamento_bracos
    ),
    StretchingSession(
        id = 3,
        title = "Alongamento de Costas",
        description = "Exercícios para aliviar a tensão nas costas e melhorar a postura.",
        bodyPart = BodyPart.BACK,
        exercises = listOf(
            StretchingExercise(
                id = 7,
                name = "Alongamento de Coluna",
                description = "Estique a coluna, mantendo a postura correta.",
                duration = "30 segundos",
                imageRes = R.drawable.alongamento_de_coluna,
                videoFileName = "videoplayback"
            ),
            StretchingExercise(
                id = 8,
                name = "Alongamento de Lombar",
                description = "Estique os músculos da região lombar.",
                duration = "30 segundos",
                imageRes = R.drawable.alongamento_de_lombar,
                videoFileName = "videoplayback"
            ),
            StretchingExercise(
                id = 9,
                name = "Alongamento de Trapézio",
                description = "Alivie a tensão no trapézio.",
                duration = "20 segundos",
                imageRes = R.drawable.alongamento_de_trapezio,
                videoFileName = "videoplayback"
            )
        ),
        isFavorite = mutableStateOf(false),
        imageRes = R.drawable.alongamento_costas
    ),
    StretchingSession(
        id = 4,
        title = "Alongamento Completo",
        description = "Exercícios para alongar o corpo inteiro.",
        bodyPart = BodyPart.FULL_BODY,
        exercises = listOf(
            StretchingExercise(
                id = 10,
                name = "Alongamento de Corpo Inteiro",
                description = "Alongue todos os principais grupos musculares.",
                duration = "60 segundos",
                imageRes = R.drawable.alongamento_de_corpo,
                videoFileName = "corpotodo"
            )
        ),
        isFavorite = mutableStateOf(false),
        imageRes = R.drawable.alongamento_corpo
    )
)
