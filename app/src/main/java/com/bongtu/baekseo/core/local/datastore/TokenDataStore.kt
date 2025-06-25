package com.bongtu.baekseo.core.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenDataStore @Inject constructor(
    private val preferenceDataStore: DataStore<Preferences>,
) {
    suspend fun getAccessToken(): String? = preferenceDataStore.data.map { preferences ->
        preferences[preferencesTokenKey] ?: TEMPORARY_TOKEN
    }.firstOrNull()

    suspend fun setAccessToken(token: String) {
        preferenceDataStore.edit { preferences ->
            preferences[preferencesTokenKey] = token
        }
    }

    suspend fun clearInfo() {
        preferenceDataStore.edit { preferences ->
            preferences.remove(preferencesTokenKey)
        }
    }

    companion object {
        private const val TOKEN_KEY = "token_key"
        private val preferencesTokenKey = stringPreferencesKey(TOKEN_KEY)

        // TODO: 엑세스 토큰 추가
        private const val TEMPORARY_TOKEN = "temporary_token"
    }
}
