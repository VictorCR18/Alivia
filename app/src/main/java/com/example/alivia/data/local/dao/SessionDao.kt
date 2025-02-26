package com.example.alivia.data.local.dao

import androidx.room.*
import com.example.alivia.data.local.entities.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity): Long

    @Update
    suspend fun updateSession(session: SessionEntity)

    @Delete
    suspend fun deleteSession(session: SessionEntity)

    @Query("SELECT * FROM sessions")
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Query("SELECT * FROM sessions WHERE id = :id LIMIT 1")
    fun getSessionById(id: Int): Flow<SessionEntity>

    @Query("SELECT COUNT(*) FROM sessions")
    suspend fun getSessionsCount(): Int

    @Query("DELETE FROM sessions")
    suspend fun deleteAllSessions()
}
