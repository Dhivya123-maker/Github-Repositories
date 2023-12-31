package com.example.nextgrowthlabstask.data.repository.user

import androidx.lifecycle.LiveData
import com.example.nextgrowthlabstask.data.local.entity.DetailRepoEntity
import com.example.nextgrowthlabstask.data.local.entity.DetailUserEntity
import com.example.nextgrowthlabstask.data.local.entity.RepoEntity
import com.example.nextgrowthlabstask.data.local.entity.UserEntity
import com.example.nextgrowthlabstask.data.remote.Result

interface UserRepository {
    fun getUsers(): LiveData<Result<List<UserEntity>>>

    fun searchUser(query: String): LiveData<Result<List<UserEntity>>>

    fun getDetailUser(username: String): LiveData<Result<DetailUserEntity>>

    fun getFollowers(username: String): LiveData<Result<List<UserEntity>>>

    fun getFollowing(username: String): LiveData<Result<List<UserEntity>>>

    fun getRepos(username: String): LiveData<Result<List<RepoEntity>>>

    fun getDetailRepo(username: String, repository: String): LiveData<Result<DetailRepoEntity>>

//    fun getFavoriteUsers(): LiveData<List<DetailUserEntity>>
//
//    fun isFavorite(id: Int): Int
//
//    suspend fun addToFavorite(user: DetailUserEntity)
//
//    suspend fun removeFromFavorite(id: Int)
}