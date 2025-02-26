package com.example.alivia.data

import com.example.alivia.data.local.dao.ExerciseDao
import com.example.alivia.data.local.dao.SessionDao
import com.example.alivia.data.local.entities.ExerciseEntity
import com.example.alivia.data.local.entities.SessionEntity
import kotlinx.coroutines.flow.Flow

class AliviaRepository(
    private val sessionDao: SessionDao,
    private val exerciseDao: ExerciseDao
) {
    fun getAllSessions(): Flow<List<SessionEntity>> =
        sessionDao.getAllSessions()

    fun getSessionById(sessionId: Int): Flow<SessionEntity?> {
        return sessionDao.getSessionById(sessionId)
    }

    suspend fun insertSession(session: SessionEntity) =
        sessionDao.insertSession(session)

    suspend fun updateSession(session: SessionEntity) =
        sessionDao.updateSession(session)

    suspend fun deleteSession(session: SessionEntity) =
        sessionDao.deleteSession(session)

    fun getExercisesBySession(sessionId: Int): Flow<List<ExerciseEntity>> =
        exerciseDao.getExercisesBySession(sessionId)

    suspend fun insertExercise(exercise: ExerciseEntity) =
        exerciseDao.insertExercise(exercise)

    suspend fun updateExercise(exercise: ExerciseEntity) =
        exerciseDao.updateExercise(exercise)

    suspend fun deleteExercise(exercise: ExerciseEntity) =
        exerciseDao.deleteExercise(exercise)

    suspend fun getSessionsCount(): Int =
        sessionDao.getSessionsCount()

    suspend fun deleteAllSessions() {
        sessionDao.deleteAllSessions()
    }

    suspend fun deleteAllExercises() {
        exerciseDao.deleteAllExercises()
    }

    suspend fun updateExerciseFavorite(exerciseId: Int, newVal: Boolean) {
        exerciseDao.updateFavorite(exerciseId, newVal)
    }


    fun getFavoriteExercises(): Flow<List<ExerciseEntity>> {
        return exerciseDao.getFavoriteExercises()
    }

    fun getExerciseByIdFlow(exerciseId: Int): Flow<ExerciseEntity?> {
        return exerciseDao.getExerciseByIdFlow(exerciseId)
    }
}