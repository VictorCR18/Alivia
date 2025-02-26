package com.example.alivia.data.local.dao

import androidx.room.*
import com.example.alivia.data.local.entities.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity): Long

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercises WHERE sessionId = :sessionId")
    fun getExercisesBySession(sessionId: Int): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE id = :exerciseId LIMIT 1")
    fun getExerciseById(exerciseId: Int): Flow<ExerciseEntity>

    @Query("DELETE FROM exercises")
    suspend fun deleteAllExercises()

    @Query("UPDATE exercises SET isFavorite = :isFavorite WHERE id = :exerciseId")
    suspend fun updateFavorite(exerciseId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM exercises WHERE isFavorite = 1")
    fun getFavoriteExercises(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    fun getExerciseByIdFlow(exerciseId: Int): Flow<ExerciseEntity?>
}
