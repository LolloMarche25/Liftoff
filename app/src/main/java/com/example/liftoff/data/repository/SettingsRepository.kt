package com.example.liftoff.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications")
    }

    val username = dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: "Space Explorer"
    }

    val email = dataStore.data.map { preferences ->
        preferences[EMAIL_KEY] ?: "spaceexplorer@liftoff.app"
    }

    val notificationsEnabled = dataStore.data.map { preferences ->
        preferences[NOTIFICATIONS_KEY] ?: true
    }

    suspend fun setUsername(username: String) = dataStore.edit { preferences ->
        preferences[USERNAME_KEY] = username
    }

    suspend fun setEmail(email: String) = dataStore.edit { preferences ->
        preferences[EMAIL_KEY] = email
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) = dataStore.edit { preferences ->
        preferences[NOTIFICATIONS_KEY] = enabled
    }
}