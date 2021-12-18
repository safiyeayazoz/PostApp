package com.example.postapp.repository

import com.example.postapp.interceptor.PostApiException
import com.example.postapp.model.Post
import com.example.postapp.model.User

interface PostRepository {
    @Throws(PostApiException::class)
    suspend fun getPostList(start: Int): List<Post>

    @Throws(PostApiException::class)
    suspend fun getUser(id: Int): User
}