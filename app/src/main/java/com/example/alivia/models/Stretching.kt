package com.example.alivia.models

import com.example.alivia.R

data class Stretching(
    val id: Int,
    val name: String,
    val duration: String,
    val targetMuscleGroup: String,
    val difficulty: String,
    val description: String,
    val imageRes: Int,
    var isFavorite: Boolean = false
)

val stretchingList = listOf(
    Stretching(
        id = 1,
        name = "Neck Stretch",
        duration = "30 seconds",
        targetMuscleGroup = "Neck",
        difficulty = "Easy",
        description = "Gently tilt your head to the side, bringing your ear toward your shoulder, and hold.",
        imageRes = R.drawable.neck_stretch
    )
)
