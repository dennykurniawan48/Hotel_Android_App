package com.skripsi.portofoliohotel.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "auth")
@ViewModelScoped
class DatastoreRepository @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.dataStore
    val flowToken: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[tokenKey] ?: ""
        }

    val flowOnBoard: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[onBoardKey] ?: false
        }

    suspend fun saveToken(token: String) {
        dataStore.edit { settings ->
            settings[tokenKey] = token
        }
    }

    suspend fun saveOnBoard() {
        dataStore.edit { settings ->
            settings[onBoardKey] = true
        }
    }

    companion object PreferencesKey {
        val tokenKey = stringPreferencesKey("_token")
        val onBoardKey = booleanPreferencesKey("_onboard")
    }
}