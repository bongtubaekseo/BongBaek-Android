package com.bongtu.baekseo.core.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConfigDataStore @Inject constructor(
    private val preferenceDataStore: DataStore<Preferences>,
) {
    fun getUpdateFlag() = preferenceDataStore.data.map { preferences ->
        preferences[UPDATE_REQUIRED_KEY] ?: false
    }

    suspend fun setUpdateFlag(required: Boolean) = preferenceDataStore.edit { preferences ->
        preferences[UPDATE_REQUIRED_KEY] = required
    }

    suspend fun clearUpdateFlag() = preferenceDataStore.edit { preferences ->
        preferences.remove(UPDATE_REQUIRED_KEY)
    }

    companion object {
        private val UPDATE_REQUIRED_KEY = booleanPreferencesKey("update_required")
    }
}
