package com.sergey.flightsearch.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("search_preferences")

class SearchPreferences(
    private val context: Context
) {

    companion object {
        val SEARCH_KEY = stringPreferencesKey("search_key")
    }

    val searchQuery: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[SEARCH_KEY] ?: ""
        }

    suspend fun saveSearch(query: String) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_KEY] = query
        }
    }
}