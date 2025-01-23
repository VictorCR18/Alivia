package com.example.alivia.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "user_prefs"

// Extensão para acessar o DataStore
private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

object DataStoreUtils {
    // Chaves para as preferências
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
    private val ANIMATIONS_KEY = booleanPreferencesKey("animations_enabled")
    private val FAVORITE_EXERCISES_KEY = stringSetPreferencesKey("favorite_exercises")

    // Funções para armazenar as preferências
    suspend fun setDarkMode(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = isEnabled
        }
    }

    suspend fun setNotificationsEnabled(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[NOTIFICATIONS_KEY] = isEnabled
        }
    }

    suspend fun setAnimationsEnabled(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ANIMATIONS_KEY] = isEnabled
        }
    }

    suspend fun setFavoriteExercises(context: Context, favorites: Set<String>) {
        context.dataStore.edit { prefs ->
            prefs[FAVORITE_EXERCISES_KEY] = favorites
        }
    }

    // Funções para recuperar as preferências
    fun isDarkModeEnabled(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[DARK_MODE_KEY] == true
        }

    fun areNotificationsEnabled(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[NOTIFICATIONS_KEY] == true
        }

    fun areAnimationsEnabled(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[ANIMATIONS_KEY] != false
        }

    fun getFavoriteExercises(context: Context): Flow<Set<String>> =
        context.dataStore.data.map { prefs ->
            prefs[FAVORITE_EXERCISES_KEY] ?: emptySet()
        }
}
