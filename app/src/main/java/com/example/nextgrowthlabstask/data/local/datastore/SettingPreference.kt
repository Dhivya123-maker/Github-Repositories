package com.example.nextgrowthlabstask.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.nextgrowthlabstask.utils.Constant.THEME_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingPreference @Inject constructor(private val dataStore: DataStore<Preferences>) {

    fun isDarkModeActive(): Flow<Boolean> =
        dataStore.data.map {
            it[THEME_KEY] ?: false
        }

    suspend fun setThemeMode(isDarkMode: Boolean) =
        dataStore.edit {
            it[THEME_KEY] = isDarkMode
        }
}