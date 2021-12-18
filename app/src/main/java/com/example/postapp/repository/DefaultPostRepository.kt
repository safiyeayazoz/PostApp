package com.example.postapp.repository

import com.example.postapp.data.PostApiService
import com.example.postapp.interceptor.PostApiException
import com.example.postapp.model.Post
import com.example.postapp.model.User
import com.example.postapp.util.updatePostList
import javax.inject.Inject

class DefaultPostRepository @Inject constructor(
    private val postApiService: PostApiService,
) : PostRepository {

    @Throws(PostApiException::class)
    override suspend fun getPostList(start: Int): List<Post> = postApiService.getPostList(start = start).updatePostList()

    @Throws(PostApiException::class)
    override suspend fun getUser(id: Int): User = postApiService.getUser(id = id)


}