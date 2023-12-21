package com.example.nextgrowthlabstask.di

import com.example.nextgrowthlabstask.data.repository.user.UserRepository
import com.example.nextgrowthlabstask.data.repository.user.UserRepositoryImpl
import com.example.nextgrowthlabstask.data.local.LocalDataSource
import com.example.nextgrowthlabstask.data.local.datastore.SettingPreference
import com.example.nextgrowthlabstask.data.local.room.UserDatabase
import com.example.nextgrowthlabstask.data.remote.RemoteDataSource
import com.example.nextgrowthlabstask.data.remote.api.ApiService
import com.example.nextgrowthlabstask.data.repository.theme.ThemeRepository
import com.example.nextgrowthlabstask.data.repository.theme.ThemeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserRepository(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): UserRepository =
        UserRepositoryImpl(remoteDataSource, localDataSource)

    @Provides
    @Singleton
    fun provideThemeRepository(localDataSource: LocalDataSource): ThemeRepository =
        ThemeRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource =
        RemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideLocalDataSource(database: UserDatabase, settingPref: SettingPreference): LocalDataSource =
        LocalDataSource(database.userDao(), settingPref)
}