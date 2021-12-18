package com.example.postapp.data

import com.example.postapp.interceptor.ApiRequestException
import com.example.postapp.model.Post
import com.example.postapp.model.User
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {

    @Throws(ApiRequestException::class,JsonParseException::class, MalformedJsonException::class)
    @GET("posts")
    suspend fun getPosts(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int = 10,
    ): List<Post>

    @Throws(ApiRequestException::class, JsonParseException::class, MalformedJsonException::class)
    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int,
    ): User

}