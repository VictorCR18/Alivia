package com.example.alivia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alivia.data.AliviaRepository
import com.example.alivia.data.local.AppDatabase
import com.example.alivia.data.local.entities.ExerciseEntity
import com.example.alivia.data.local.entities.SessionEntity
import com.example.alivia.models.stretchingSessions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = AliviaRepository(db.sessionDao(), db.exerciseDao())

    val sessionsFlow = repository.getAllSessions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addSession(session: SessionEntity) {
        viewModelScope.launch {
            repository.insertSession(session)
        }
    }

    fun deleteSession(session: SessionEntity) {
        viewModelScope.launch {
            repository.deleteSession(session)
        }
    }

    fun getSessionByIdFlow(id: Int) = repository.getSessionById(id)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setFavoriteExercise(id: Int, newVal: Boolean) {
        viewModelScope.launch {
            repository.updateExerciseFavorite(id, newVal)
        }
    }

    fun getExerciseByIdFlow(exerciseId: Int): Flow<ExerciseEntity?> {
        return repository.getExerciseByIdFlow(exerciseId)
    }

    fun getExercisesBySessionFlow(sessionId: Int) = repository.getExercisesBySession(sessionId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteExercisesFlow = repository.getFavoriteExercises()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.deleteAllSessions()
            repository.deleteAllExercises()

            stretchingSessions.forEach { oldSession ->
                repository.insertSession(
                    SessionEntity(
                        id = oldSession.id,
                        title = oldSession.title,
                        description = oldSession.description,
                        bodyPart = oldSession.bodyPart.toString(),
                        imageRes = oldSession.imageRes
                    )
                )

                oldSession.exercises.forEach { oldExercise ->
                    repository.insertExercise(
                        ExerciseEntity(
                            id = oldExercise.id,
                            sessionId = oldSession.id,
                            name = oldExercise.name,
                            description = oldExercise.description,
                            duration = oldExercise.duration,
                            imageRes = oldExercise.imageRes,
                            videoFileName = oldExercise.videoFileName
                        )
                    )
                }
            }
        }
    }

}


