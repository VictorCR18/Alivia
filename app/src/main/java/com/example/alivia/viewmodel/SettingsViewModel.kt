package com.example.alivia.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alivia.data.preferences.DataStoreUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModelFactory(
    private val context: Context,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SettingsViewModel(private val context: Context) : ViewModel() {
    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> get() = _isDarkModeEnabled.asStateFlow()

    private val _isNotificationsEnabled = MutableStateFlow(false)
    val isNotificationsEnabled: StateFlow<Boolean> get() = _isNotificationsEnabled.asStateFlow()

    private val _areAnimationsEnabled = MutableStateFlow(true) // Estado inicial para animações
    val areAnimationsEnabled: StateFlow<Boolean> get() = _areAnimationsEnabled.asStateFlow()

    private val _favoriteExercises = MutableStateFlow<Set<String>>(emptySet())
    val favoriteExercises: StateFlow<Set<String>> get() = _favoriteExercises.asStateFlow()

    init {
        // Carrega as preferências iniciais do DataStore
        viewModelScope.launch {
            _isDarkModeEnabled.value = DataStoreUtils.isDarkModeEnabled(context).first()
            _isNotificationsEnabled.value = DataStoreUtils.areNotificationsEnabled(context).first()
            _areAnimationsEnabled.value = DataStoreUtils.areAnimationsEnabled(context).first()
            _favoriteExercises.value = DataStoreUtils.getFavoriteExercises(context).first()
        }
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        _isDarkModeEnabled.value = enabled
        viewModelScope.launch {
            DataStoreUtils.setDarkMode(context, enabled)
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        _isNotificationsEnabled.value = enabled
        viewModelScope.launch {
            DataStoreUtils.setNotificationsEnabled(context, enabled)
        }
    }

    fun setAnimationsEnabled(enabled: Boolean) {
        _areAnimationsEnabled.value = enabled
        viewModelScope.launch {
            DataStoreUtils.setAnimationsEnabled(context, enabled)
        }
    }

    fun addFavoriteExercise(exerciseId: String) {
        val updatedFavorites = _favoriteExercises.value.toMutableSet()
        updatedFavorites.add(exerciseId)
        _favoriteExercises.value = updatedFavorites
        viewModelScope.launch {
            DataStoreUtils.setFavoriteExercises(context, updatedFavorites)
        }
    }

    fun removeFavoriteExercise(exerciseId: String) {
        val updatedFavorites = _favoriteExercises.value.toMutableSet()
        updatedFavorites.remove(exerciseId)
        _favoriteExercises.value = updatedFavorites
        viewModelScope.launch {
            DataStoreUtils.setFavoriteExercises(context, updatedFavorites)
        }
    }

    fun clearFavoriteExercises() {
        _favoriteExercises.value = emptySet()
        viewModelScope.launch {
            DataStoreUtils.setFavoriteExercises(context, emptySet())
        }
    }
}
