package com.example.nextgrowthlabstask.data.local

import androidx.lifecycle.LiveData
import com.example.nextgrowthlabstask.data.local.datastore.SettingPreference
import com.example.nextgrowthlabstask.data.local.entity.DetailUserEntity
import com.example.nextgrowthlabstask.data.local.entity.UserEntity
import com.example.nextgrowthlabstask.data.local.room.UserDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val pref: SettingPreference
) {
    suspend fun addUsers(users: List<UserEntity>) = userDao.addUsers(users)

    suspend fun deleteAllUsers() = userDao.deleteAllUsers()

    fun getUsers() = userDao.getUsers()

//    fun getFavoriteUsers(): LiveData<List<DetailUserEntity>> = userDao.getFavoriteUsers()
//
//    fun isFavoriteUser(id: Int) = userDao.isFavorite(id)

//    suspend fun addToFavorite(user: DetailUserEntity) = userDao.addToFavorite(user)
//
//    suspend fun removeFromFavorite(id: Int) = userDao.removeFromFavorite(id)

    fun isDarkModeActive() = pref.isDarkModeActive()

    suspend fun setThemeMode(isDarkMode: Boolean) = pref.setThemeMode(isDarkMode)
}