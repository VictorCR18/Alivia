package com.example.alivia.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

// DTOs para Sessão e Exercício
data class SessionDto(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val bodyPart: String? = null,
    val imageRes: Int? = null
)

data class ExerciseDto(
    val id: Int? = null,
    val sessionId: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val duration: String? = null,
    val imageRes: Int? = null,
    val videoFileName: String? = null,
    val isFavorite: Boolean? = null
)

interface FirebaseApi {
    @GET("sessions.json")
    suspend fun getAllSessions(): List<SessionDto?>?

    @PUT("sessions/{sessionId}.json")
    suspend fun putSession(
        @Path("sessionId") sessionId: String,
        @Body session: SessionDto
    ): Response<SessionDto>

    @DELETE("sessions/{sessionId}.json")
    suspend fun deleteSession(
        @Path("sessionId") sessionId: String
    ): Response<Void>

    @GET("exercises.json")
    suspend fun getAllExercises(): List<ExerciseDto?>?

    @PUT("exercises/{exerciseId}.json")
    suspend fun putExercise(
        @Path("exerciseId") exerciseId: String,
        @Body exercise: ExerciseDto
    ): Response<ExerciseDto>

    @DELETE("exercises/{exerciseId}.json")
    suspend fun deleteExercise(
        @Path("exerciseId") exerciseId: String
    ): Response<Void>
}