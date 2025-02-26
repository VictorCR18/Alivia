package com.example.alivia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alivia.data.remote.FirebaseRepository
import com.example.alivia.data.remote.RetrofitClient
import com.example.alivia.data.remote.SessionDto
import com.example.alivia.data.remote.ExerciseDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirebaseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FirebaseRepository(RetrofitClient.api)

    private val _sessionsFlow = MutableStateFlow<List<SessionDto>>(emptyList())
    val sessionsFlow: StateFlow<List<SessionDto>> get() = _sessionsFlow

    private val _exercisesFlow = MutableStateFlow<List<ExerciseDto>>(emptyList())
    val exercisesFlow: StateFlow<List<ExerciseDto>> get() = _exercisesFlow


    fun toggleFavoriteExercise(exercise: ExerciseDto) {
        viewModelScope.launch {
            val updatedValue = exercise.isFavorite?.not() ?: true
            val updatedExercise = exercise.copy(isFavorite = updatedValue)
            val success = repository.putExercise(updatedExercise)
            if (success) {
                _exercisesFlow.value = _exercisesFlow.value.map {
                    if (it.id == updatedExercise.id) updatedExercise else it
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            // Seed no Firebase com os dados iniciais
            val sessions = repository.fetchAllSessions()
            if (sessions.isEmpty()) {
                repository.seedFirebase()
            }

            // Após o seed, busca todas as sessões e exercícios
            _sessionsFlow.value = repository.fetchAllSessions()
            _exercisesFlow.value = repository.fetchAllExercises()

        }
    }

    // Você pode adicionar funções para refresh, upload individual, etc.
    fun refreshData() {
        viewModelScope.launch {
            _sessionsFlow.value = repository.fetchAllSessions()
            _exercisesFlow.value = repository.fetchAllExercises()
        }
    }


}
