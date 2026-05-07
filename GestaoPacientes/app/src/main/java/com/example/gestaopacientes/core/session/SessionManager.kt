package com.example.gestaopacientes.core.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "session")

class SessionManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
    }

    suspend fun saveToken(token: String){
        context.dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    fun getToken(): Flow<String?>{
        return context.dataStore.data.map {
            it[TOKEN_KEY]
        }
    }

    suspend fun clear(){
        context.dataStore.edit {
            it.clear()
        }
    }
}