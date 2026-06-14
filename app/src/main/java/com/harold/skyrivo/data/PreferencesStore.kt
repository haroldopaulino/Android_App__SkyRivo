package com.harold.skyrivo.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "skyrivo_settings")

interface PreferencesStore {
    val lastCity: Flow<String?>
    suspend fun saveLastCity(city: String)
}

class DataStorePreferencesStore(private val context: Context) : PreferencesStore {
    private val lastCityKey = stringPreferencesKey("last_city")

    override val lastCity: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[lastCityKey]
    }

    override suspend fun saveLastCity(city: String) {
        context.dataStore.edit { preferences ->
            preferences[lastCityKey] = city
        }
    }
}
