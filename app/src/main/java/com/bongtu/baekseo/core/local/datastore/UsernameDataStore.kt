package com.bongtu.baekseo.core.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsernameDataStore @Inject constructor(
    private val preferenceDataStore: DataStore<Preferences>,
) {
    suspend fun getUsername(): String? = preferenceDataStore.data.map { preferences ->
        preferences[preferencesUsernameKey] ?: USER_NAME_KEY
    }.firstOrNull()

    suspend fun setUsername(name: String) {
        preferenceDataStore.edit { preferences ->
            preferences[preferencesUsernameKey] = name
        }
    }

    suspend fun clearInfo() {
        preferenceDataStore.edit { preferences ->
            preferences.remove(preferencesUsernameKey)
        }
    }

    companion object {
        private const val USER_NAME_KEY = "user_name_key"
        private val preferencesUsernameKey = stringPreferencesKey(USER_NAME_KEY)
    }
}