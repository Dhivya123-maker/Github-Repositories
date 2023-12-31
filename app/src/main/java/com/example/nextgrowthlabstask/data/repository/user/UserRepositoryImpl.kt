package com.example.nextgrowthlabstask.data.repository.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.nextgrowthlabstask.data.local.LocalDataSource
import com.example.nextgrowthlabstask.data.local.entity.DetailRepoEntity
import com.example.nextgrowthlabstask.data.local.entity.DetailUserEntity
import com.example.nextgrowthlabstask.data.local.entity.RepoEntity
import com.example.nextgrowthlabstask.data.local.entity.UserEntity
import com.example.nextgrowthlabstask.data.remote.RemoteDataSource
import com.example.nextgrowthlabstask.data.remote.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): UserRepository {

    override fun getUsers(): LiveData<Result<List<UserEntity>>> =
        liveData {
            try {
                val response = remoteDataSource.getUsers()
                val userList = response.map {
                    UserEntity(
                        it.userId,
                        it.avatarUrl,
                        it.login,
                        it.type
                    )
                }
                localDataSource.deleteAllUsers()
                localDataSource.addUsers(userList)
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }

            val localData: LiveData<Result<List<UserEntity>>> =
                localDataSource.getUsers().map { Result.Success(it) }
            emitSource(localData)
        }

    override fun searchUser(query: String): LiveData<Result<List<UserEntity>>> =
        liveData {
            try {
                val response = remoteDataSource.searchUser(query)
                val userList = response.items.map {
                    UserEntity(
                        it.userId,
                        it.avatarUrl,
                        it.login,
                        it.type
                    )
                }
                emit(Result.Success(userList))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    override fun getDetailUser(username: String): LiveData<Result<DetailUserEntity>> =
        liveData {
            try {
                val response = remoteDataSource.getDetailUser(username)
                response.apply {
                    val detailEntity = DetailUserEntity(
                        null,
                        userId,
                        login,
                        name,
                        type,
                        bio,
                        company,
                        location,
                        blog,
                        publicRepos,
                        followers,
                        avatarUrl,
                        following
                    )
                    emit(Result.Success(detailEntity))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    override fun getFollowers(username: String): LiveData<Result<List<UserEntity>>> =
        liveData {
            try {
                val response = remoteDataSource.getFollowers(username)
                val followerList = response.map {
                    UserEntity(
                        it.userId,
                        it.avatarUrl,
                        it.login,
                        it.type
                    )
                }
                emit(Result.Success(followerList))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    override fun getFollowing(username: String): LiveData<Result<List<UserEntity>>> =
        liveData {
            try {
                val response = remoteDataSource.getFollowing(username)
                val followingList = response.map {
                    UserEntity(
                        it.userId,
                        it.avatarUrl,
                        it.login,
                        it.type
                    )
                }
                emit(Result.Success(followingList))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    override fun getRepos(username: String): LiveData<Result<List<RepoEntity>>> =
        liveData {
            try {
                val response = remoteDataSource.getRepos(username)
                val repoList = response.map {
                    RepoEntity(
                        it.updatedAt,
                        it.stargazersCount,
                        it.visibility,
                        it.name,
                        it.description,
                        it.language,
                        it.owner.login,
                        it.id
                    )
                }.sortedByDescending { it.updatedAt }
                emit(Result.Success(repoList))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    override fun getDetailRepo(
        username: String, repository: String
    ): LiveData<Result<DetailRepoEntity>> = liveData {
        try {
            val response = remoteDataSource.getDetailRepo(username, repository)
            with(response) {
                val detailEntity = DetailRepoEntity(
                    fullName,
                    stargazersCount,
                    openIssuesCount,
                    networkCount,
                    htmlUrl,
                    name,
                    description,
                    language,
                    subscribersCount,
                    id,
                    watchersCount,
                    forksCount,
                    updatedAt,
                    topics
                )
                emit(Result.Success(detailEntity))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

//    override fun getFavoriteUsers(): LiveData<List<DetailUserEntity>> =
//        localDataSource.getFavoriteUsers()
//
//    override fun isFavorite(id: Int): Int =
//        localDataSource.isFavoriteUser(id)
//
//    override suspend fun addToFavorite(user: DetailUserEntity) =
//        localDataSource.addToFavorite(user)
//
//    override suspend fun removeFromFavorite(id: Int) =
//        localDataSource.removeFromFavorite(id)
}