package com.example.alivia.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercises",
    foreignKeys = [
        ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val sessionId: Int,
    val name: String,
    val description: String,
    val duration: String,
    val imageRes: Int,
    val videoFileName: String,
    val isFavorite: Boolean = false
)
