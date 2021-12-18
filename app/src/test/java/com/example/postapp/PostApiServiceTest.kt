package com.example.postapp

import com.example.postapp.data.PostApi
import com.example.postapp.data.PostApiService
import com.example.postapp.interceptor.ApiRequestException
import com.example.postapp.interceptor.PostApiException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostApiServiceTest {

    @Mock
    private lateinit var postApi: PostApi

    private lateinit var subject: PostApiService

    @Before
    fun setUp() {
        subject = PostApiService(
            postApi,
        )
    }

    // GetPostList Region
    @Test(expected = PostApiException.NetworkError::class)
    fun `when getPostList is performed and there is network error, should throw an exception`() = runBlockingTest {
        whenever(postApi.getPosts(any(), any())).thenThrow(ApiRequestException.NetworkError)

        subject.getPostList(start = 0)
    }

    @Test(expected = PostApiException.ServerError::class)
    fun `when getPostList is performed and there is bad response error, should throw an exception`() = runBlockingTest {
        whenever(postApi.getPosts(any(), any())).thenThrow(mock<ApiRequestException.BadResponse>())

        subject.getPostList(start = 0)
    }

    @Test(expected = PostApiException.InvalidRequestError::class)
    fun `when getPostList is performed and there is bad request error, should throw an exception`() = runBlockingTest {
        whenever(postApi.getPosts(any(), any())).thenThrow(mock<ApiRequestException.BadRequest>())

        subject.getPostList(start = 0)
    }

    @Test(expected = PostApiException.ParsingError::class)
    fun `when getPostList is performed and fails to parse a response, should throw an exception`() = runBlockingTest {
        whenever(postApi.getPosts(any(), any())).thenThrow(mock<JsonParseException>())

        subject.getPostList(start = 0)
    }

    @Test(expected = PostApiException.ParsingError::class)
    fun `when getPostList is performed and receives a malformed json, should throw an exception`() = runBlockingTest {
        whenever(postApi.getPosts(any(), any())).thenThrow(mock<MalformedJsonException>())

        subject.getPostList(start = 0)
    }

    @Test
    fun `when getPostList is performed and successful parse a response, should make a call with proper values`() = runBlockingTest {
        subject.getPostList(start = 0)

        verify(postApi).getPosts(start = 0, limit = 10)
    }
    //endregion

    // GetUser Region
    @Test(expected = PostApiException.NetworkError::class)
    fun `when getUser is performed and there is network error, should throw an exception`() = runBlockingTest {
        whenever(postApi.getUser(any())).thenThrow(ApiRequestException.NetworkError)

        subject.getUser(id = 1)
    }

    @Test(expected = PostApiException.ServerError::class)
    fun `when getUser is performed and there is bad response error, should throw an exception`() = runBlockingTest {
        whenever(postApi.getUser(any())).thenThrow(mock<ApiRequestException.BadResponse>())

        subject.getUser(id = 1)
    }

    @Test(expected = PostApiException.InvalidRequestError::class)
    fun `when getUser is performed and there is bad request error, should throw an exception`() = runBlockingTest {
        whenever(postApi.getUser(any())).thenThrow(mock<ApiRequestException.BadRequest>())

        subject.getUser(id = 1)
    }

    @Test(expected = PostApiException.ParsingError::class)
    fun `when getUser is performed and fails to parse a response, should throw an exception`() = runBlockingTest {
        whenever(postApi.getUser(any())).thenThrow(mock<JsonParseException>())

        subject.getUser(id = 1)
    }

    @Test(expected = PostApiException.ParsingError::class)
    fun `when getUser is performed and receives a malformed json, should throw an exception`() = runBlockingTest {
        whenever(postApi.getUser(any())).thenThrow(mock<MalformedJsonException>())

        subject.getUser(id = 1)
    }

    @Test
    fun `when getUser is performed and successful parse a response, should make a call with proper values`() = runBlockingTest {
        subject.getUser(id = 1)

        verify(postApi).getUser(id = 1)
    }
    //endregion
}