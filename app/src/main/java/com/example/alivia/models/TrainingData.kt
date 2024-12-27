package com.example.alivia.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.alivia.R

data class Training(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val isFavorite: MutableState<Boolean> = mutableStateOf(false),
    val isSubscribed: MutableState<Boolean> = mutableStateOf(false),
    val imageRes: Int
)

val trainingList = listOf(
    Training(
        id = 1,
        title = "Conferência de Tecnologia 2024",
        description = "Tendências em tecnologia.",
        date = "2024-12-15",
        location = "Parque Tecnológico",
        isFavorite = mutableStateOf(false),
        isSubscribed = mutableStateOf(false),
        imageRes = R.drawable.img3
    ),
//    Event(
//        id = 2,
//        title = "Feira de Inovação Sustentável",
//        description = "Soluções sustentáveis para o futuro.",
//        date = "2024-12-20",
//        location = "Centro de Convenções Verde",
//        isFavorite = mutableStateOf(false),
//        isSubscribed = mutableStateOf(false),
//        imageRes = R.drawable.img2
//    ),
//    Event(
//        id = 3,
//        title = "Encontro de Startups 2024",
//        description = "Networking e ideias inovadoras.",
//        date = "2024-12-22",
//        location = "Auditório Empresarial",
//        isFavorite = mutableStateOf(false),
//        isSubscribed = mutableStateOf(false),
//        imageRes = R.drawable.img3
//    ),
//    Event(
//        id = 4,
//        title = "Maratona de Programação",
//        description = "Desafios de código para equipes.",
//        date = "2024-12-25",
//        location = "Campus da Universidade X",
//        isFavorite = mutableStateOf(false),
//        isSubscribed = mutableStateOf(false),
//        imageRes = R.drawable.img4
//    ),
//    Event(
//        id = 5,
//        title = "Palestra: IA e o Futuro",
//        description = "Impactos da inteligência artificial.",
//        date = "2024-12-28",
//        location = "Teatro Digital",
//        isFavorite = mutableStateOf(false),
//        isSubscribed = mutableStateOf(false),
//        imageRes = R.drawable.img5
//    ),
//    Event(
//        id = 6,
//        title = "Hackathon de Jogos 2024",
//        description = "Criação de jogos em 48 horas.",
//        date = "2025-01-03",
//        location = "Espaço Criativo",
//        isFavorite = mutableStateOf(false),
//        isSubscribed = mutableStateOf(false),
//        imageRes = R.drawable.img6
//    ),
//    Event(
//        id = 7,
//        title = "Workshop: Design Thinking",
//        description = "Estratégias para inovação.",
//        date = "2025-01-08",
//        location = "Laboratório de Ideias",
//        isFavorite = mutableStateOf(false),
//        isSubscribed = mutableStateOf(false),
//        imageRes = R.drawable.img7
//    ),
//    Event(
//        id = 8,
//        title = "Expo Tecnológica 2025",
//        description = "Exposição de novos gadgets.",
//        date = "2025-01-12",
//        location = "Pavilhão de Exposições",
//        isFavorite = mutableStateOf(false),
//        isSubscribed = mutableStateOf(false),
//        imageRes = R.drawable.img8
//    ),
//    Event(
//        id = 9,
//        title = "Seminário: Cloud Computing",
//        description = "Desafios e oportunidades na nuvem.",
//        date = "2025-01-15",
//        location = "Centro de TI Avançada",
//        isFavorite = mutableStateOf(false),
//        isSubscribed = mutableStateOf(false),
//        imageRes = R.drawable.img9
//    ),
//    Event(
//        id = 10,
//        title = "Festival de Robótica",
//        description = "Competição e exposição de robôs.",
//        date = "2025-01-20",
//        location = "Arena de Robótica",
//        isFavorite = mutableStateOf(false),
//        isSubscribed = mutableStateOf(false),
//        imageRes = R.drawable.img10
//    )
)

