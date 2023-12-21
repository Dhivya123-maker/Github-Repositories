package com.example.nextgrowthlabstask.utils

import androidx.datastore.preferences.core.booleanPreferencesKey


object Constant {
    const val BASE_URL = "https://api.github.com/"
    const val SETTING_PREFERENCE = "setting_preference"
    val THEME_KEY = booleanPreferencesKey("theme_key")
}