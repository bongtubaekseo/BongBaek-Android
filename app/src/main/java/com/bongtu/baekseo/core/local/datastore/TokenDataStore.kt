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
    suspend fun getAccessToken(): String? = preferenceDataStore.data.map {
        it[preferencesAccessTokenKey]
    }.firstOrNull()

    suspend fun getRefreshToken(): String? = preferenceDataStore.data.map {
        it[preferencesRefreshTokenKey]
    }.firstOrNull()

    suspend fun setTokens(accessToken: String, refreshToken: String) {
        preferenceDataStore.edit { preferences ->
            preferences[preferencesAccessTokenKey] = accessToken
            preferences[preferencesRefreshTokenKey] = refreshToken
        }
    }

    suspend fun clearInfo() {
        preferenceDataStore.edit { preferences ->
            preferences.remove(preferencesAccessTokenKey)
            preferences.remove(preferencesRefreshTokenKey)
        }
    }

    suspend fun getKakaoId(): String = preferenceDataStore.data.map { preferences ->
        preferences[preferencesKakaoIdKey]
    }.firstOrNull() ?: KAKAO_ID_KEY

    suspend fun setKakaoId(kakaoId: Long) {
        preferenceDataStore.edit { preferences ->
            preferences[preferencesKakaoIdKey] = kakaoId.toString()
        }
    }

    suspend fun clearKakaoId() {
        preferenceDataStore.edit { preferences ->
            preferences.remove(preferencesKakaoIdKey)
        }
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val KAKAO_ID_KEY = "kakao_id"

        private val preferencesAccessTokenKey = stringPreferencesKey(ACCESS_TOKEN_KEY)
        private val preferencesRefreshTokenKey = stringPreferencesKey(REFRESH_TOKEN_KEY)
        private val preferencesKakaoIdKey = stringPreferencesKey(KAKAO_ID_KEY)
    }
}
