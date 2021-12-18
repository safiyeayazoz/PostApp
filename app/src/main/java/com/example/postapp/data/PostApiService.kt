package com.example.postapp.data

import com.example.postapp.interceptor.PostApiException
import com.example.postapp.interceptor.performNetworkCall
import com.example.postapp.model.Post
import com.example.postapp.model.User
import javax.inject.Inject

class PostApiService @Inject constructor(
    private val postApi: PostApi,
){
    @Throws(PostApiException::class)
    suspend fun getPostList(start: Int): List<Post> = postApi.makeCall { getPosts(start = start) }

    @Throws(PostApiException::class)
    suspend fun getUser(id: Int): User = postApi.makeCall { getUser(id = id) }

    @Throws(PostApiException::class)
    private suspend fun <T> PostApi.makeCall(action: suspend PostApi.() -> T) = performNetworkCall { action() }

}