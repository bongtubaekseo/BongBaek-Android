package com.bongtu.baekseo.core.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ApiKeyDataStore @Inject constructor(
    private val preferenceDataStore: DataStore<Preferences>,
) {
    suspend fun getApiKey(): String = preferenceDataStore.data.map { preferences ->
        preferences[preferencesApiKey]
    }.firstOrNull() ?: API_KEY

    suspend fun setApiKey(apiKey: String) {
        preferenceDataStore.edit { preferences ->
            preferences[preferencesApiKey] = apiKey
        }
    }

    suspend fun clearInfo() {
        preferenceDataStore.edit { preferences ->
            preferences.remove(preferencesApiKey)
        }
    }

    companion object {
        private const val API_KEY = "api_key"
        private val preferencesApiKey = stringPreferencesKey(API_KEY)
    }
}
