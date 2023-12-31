package com.example.nextgrowthlabstask.data.remote.api

import com.example.nextgrowthlabstask.data.remote.response.detail.DetailUserResponse
import com.example.nextgrowthlabstask.data.remote.response.detailrepo.DetailRepoResponse
import com.example.nextgrowthlabstask.data.remote.response.repo.RepoResponse
import com.example.nextgrowthlabstask.data.remote.response.search.SearchResponse
import com.example.nextgrowthlabstask.data.remote.response.user.UserResponse
import com.example.nextgrowthlabstask.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
   // @Headers("Authorization: ${Constant.TOKEN}")
    @GET("users")
    suspend fun getUsers(): List<UserResponse>

   // @Headers("Authorization: ${Constant.TOKEN}")
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): SearchResponse

   // @Headers("Authorization: ${Constant.TOKEN}")
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUserResponse

   // @Headers("Authorization: ${Constant.TOKEN}")
    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<UserResponse>

   // @Headers("Authorization: ${Constant.TOKEN}")
    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): List<UserResponse>

   // @Headers("Authorization: ${Constant.TOKEN}")
    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String
    ): List<RepoResponse>

    //@Headers("Authorization: ${Constant.TOKEN}")
    @GET("repos/{username}/{repository}")
    suspend fun getDetailRepo(
        @Path("username") username: String,
        @Path("repository") repository: String
    ): DetailRepoResponse
}