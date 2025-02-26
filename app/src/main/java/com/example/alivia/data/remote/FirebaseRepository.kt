package com.example.alivia.data.remote

import android.util.Log
import com.example.alivia.models.stretchingSessions

class FirebaseRepository(private val api: FirebaseApi) {

    suspend fun fetchAllSessions(): List<SessionDto> {
        return try {
            val resultList = api.getAllSessions()
            resultList?.filterNotNull() ?: emptyList()
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Erro ao fetchAllSessions: ${e.message}")
            emptyList()
        }
    }


    suspend fun fetchAllExercises(): List<ExerciseDto> {
        return try {
            val resultList = api.getAllExercises()
            resultList?.filterNotNull() ?: emptyList()
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Erro ao fetchAllExercises: ${e.message}")
            emptyList()
        }
    }

    suspend fun putSession(sessionDto: SessionDto): Boolean {
        return try {
            val firebaseKey = sessionDto.id.toString()
            val response = api.putSession(firebaseKey, sessionDto)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Erro ao putSession: ${e.message}")
            false
        }
    }

    suspend fun putExercise(dto: ExerciseDto): Boolean {
        return try {
            val key = dto.id.toString()
            val response = api.putExercise(key, dto)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Erro ao putExercise: ${e.message}")
            false
        }
    }

    suspend fun seedFirebase() {
        // Para cada sessão em stretchingSessions, insere no Firebase
        stretchingSessions.forEach { oldSession ->
            val sessionDto = SessionDto(
                id = oldSession.id,
                title = oldSession.title,
                description = oldSession.description,
                bodyPart = oldSession.bodyPart.toString(),
                imageRes = oldSession.imageRes
            )
            putSession(sessionDto)

            oldSession.exercises.forEach { oldExercise ->
                val exerciseDto = ExerciseDto(
                    id = oldExercise.id,
                    sessionId = oldSession.id,
                    name = oldExercise.name,
                    description = oldExercise.description,
                    duration = oldExercise.duration,
                    imageRes = oldExercise.imageRes,
                    videoFileName = oldExercise.videoFileName,
                    isFavorite = false
                )
                putExercise(exerciseDto)
            }
        }
    }
}