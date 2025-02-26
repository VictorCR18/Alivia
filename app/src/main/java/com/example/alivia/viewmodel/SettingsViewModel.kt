package com.example.alivia.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alivia.data.preferences.DataStoreUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalTime

class SettingsViewModelFactory(
    private val context: Context,
) : ViewModelProvider.NewInstanceFactory() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class SettingsViewModel(private val context: Context) : ViewModel() {
    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> get() = _isDarkModeEnabled.asStateFlow()

    private val _isNotificationsEnabled = MutableStateFlow(false)
    val isNotificationsEnabled: StateFlow<Boolean> get() = _isNotificationsEnabled.asStateFlow()

    private val _areAnimationsEnabled = MutableStateFlow(true) // Estado inicial para animações
    val areAnimationsEnabled: StateFlow<Boolean> get() = _areAnimationsEnabled.asStateFlow()

    private val _favoriteExercises = MutableStateFlow<Set<String>>(emptySet())
    val favoriteExercises: StateFlow<Set<String>> get() = _favoriteExercises.asStateFlow()

    // Novo estado para armazenar a seleção do tema (Claro, Escuro, Automático)
    private val _themeSelection = MutableStateFlow("Automático") // Estado inicial é "Automático"
    val themeSelection: StateFlow<String> get() = _themeSelection.asStateFlow()

    init {
        // Carrega as preferências iniciais do DataStore
        viewModelScope.launch {
            _isDarkModeEnabled.value = DataStoreUtils.isDarkModeEnabled(context).first()
            _isNotificationsEnabled.value = DataStoreUtils.areNotificationsEnabled(context).first()
            _areAnimationsEnabled.value = DataStoreUtils.areAnimationsEnabled(context).first()
            _favoriteExercises.value = DataStoreUtils.getFavoriteExercises(context).first()
            _themeSelection.value = DataStoreUtils.getThemeSelection(context).first() // Carregar seleção de tema
            applyThemeBasedOnTime() // Verifica e aplica o tema com base na hora do dia
        }
    }

    // Métodos para atualizar as preferências
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

    // Adiciona ou remove exercícios favoritos
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

    // Função para mudar a seleção de tema
    @RequiresApi(Build.VERSION_CODES.O)
    fun setThemeSelection(selection: String) {
        _themeSelection.value = selection
        viewModelScope.launch {
            DataStoreUtils.setThemeSelection(context, selection)
            applyThemeBasedOnTime() // Atualiza o tema com base na nova seleção
        }
    }

    // Função para determinar o tema automático baseado na hora do dia
    fun isAutoTheme(): Boolean {
        // Verifica se o tema selecionado é "Automático"
        return _themeSelection.value == "Automático"
    }

    // Função para aplicar o tema com base na hora do dia
    @RequiresApi(Build.VERSION_CODES.O)
    private fun applyThemeBasedOnTime() {
        if (isAutoTheme()) {
            val currentTime = LocalTime.now()
            _isDarkModeEnabled.value = currentTime.isAfter(LocalTime.of(18, 0)) || currentTime.isBefore(LocalTime.of(6, 0))
        }
    }

    // Função para determinar se o tema deve ser escuro ou claro baseado na hora
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentThemeMode(): Boolean {
        val currentTime = LocalTime.now()
        return if (isAutoTheme()) {
            // Modo escuro entre 18h e 6h, modo claro de 6h a 18h
            currentTime.isAfter(LocalTime.of(18, 0)) || currentTime.isBefore(LocalTime.of(6, 0))
        } else {
            // Se não for automático, depende da configuração manual
            _isDarkModeEnabled.value
        }
    }
}