package com.kevinmedia.submission1githubuser.api

import com.kevinmedia.submission1githubuser.DetailUser
import com.kevinmedia.submission1githubuser.GithubUser
import com.kevinmedia.submission1githubuser.SearchUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("users")
    fun getUsers(
        @Header("Authorization") token: String
    ): Call<List<GithubUser>>

    @GET("search/users")
    fun getUserSearch(
        @Query("q") login: String,
        @Header("Authorization") token: String
    ): Call<SearchUsers>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<DetailUser>

    @GET("users/{login}/followers")
    fun getAllFollowers(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<List<GithubUser>>

    //memanggil list following
    @GET("users/{login}/following")
    fun getAllFollowings(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<List<GithubUser>>
}