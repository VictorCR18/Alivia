package com.example.alivia.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val description: String,
    val bodyPart: String,
    val imageRes: Int
)
