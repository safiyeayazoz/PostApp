package com.example.postapp

import com.example.postapp.data.PostApiService
import com.example.postapp.interceptor.PostApiException
import com.example.postapp.model.Post
import com.example.postapp.model.User
import com.example.postapp.repository.DefaultPostRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DefaultPostRepositoryTest {

    @Mock
    private lateinit var postApiService: PostApiService

    private lateinit var subject: DefaultPostRepository

    @Before
    fun setUp() {
        subject = DefaultPostRepository(
            postApiService,
        )
    }

    // GetPostList Region
    @Test(expected = PostApiException.NetworkError::class)
    fun `when getPostList is performed and there is network error, should throw an exception`() =
        runBlockingTest {
            whenever(postApiService.getPostList(any())).thenThrow(PostApiException.NetworkError)

            subject.getPostList(start = 0)
        }

    @Test(expected = PostApiException.ServerError::class)
    fun `when getPostList is performed and there is bad response error, should throw an exception`() =
        runBlockingTest {
            whenever(postApiService.getPostList(any())).thenThrow(mock<PostApiException.ServerError>())

            subject.getPostList(start = 0)
        }

    @Test(expected = PostApiException.InvalidRequestError::class)
    fun `when getPostList is performed and there is bad request error, should throw an exception`() =
        runBlockingTest {
            whenever(postApiService.getPostList(any())).thenThrow(mock<PostApiException.InvalidRequestError>())

            subject.getPostList(start = 0)
        }

    @Test(expected = PostApiException.ParsingError::class)
    fun `when getPostList is performed and fails to parse a response, should throw an exception`() =
        runBlockingTest {
            whenever(postApiService.getPostList(any())).thenThrow(mock<PostApiException.ParsingError>())

            subject.getPostList(start = 0)
        }

    @Test
    fun `when getPostList is performed and successful parse a response, should make a call with proper values`() =
        runBlockingTest {
            val postResponse = Post(
                userId = 1,
                id = 2,
                title = "title",
                body = "test\nbody",
                postUiBody = "",
            )
            whenever(postApiService.getPostList(start = 0)).thenReturn(listOf(postResponse))

            val actual = subject.getPostList(start = 0)

            assertThat(actual[0].userId).isEqualTo(1)
            assertThat(actual[0].id).isEqualTo(2)
            assertThat(actual[0].title).isEqualTo("title")
            assertThat(actual[0].body).isEqualTo("test body")
            assertThat(actual[0].postUiBody).isEqualTo("test body...")
        }
    //endregion

    //GetUser region
    @Test(expected = PostApiException.NetworkError::class)
    fun `when getUser is performed and there is network error, should throw an exception`() =
        runBlockingTest {
            whenever(postApiService.getUser(any())).thenThrow(PostApiException.NetworkError)

            subject.getUser(id = 1)
        }

    @Test(expected = PostApiException.ServerError::class)
    fun `when getUser is performed and there is bad response error, should throw an exception`() =
        runBlockingTest {
            whenever(postApiService.getUser(any())).thenThrow(mock<PostApiException.ServerError>())

            subject.getUser(id = 1)
        }

    @Test(expected = PostApiException.InvalidRequestError::class)
    fun `when getUser is performed and there is bad request error, should throw an exception`() =
        runBlockingTest {
            whenever(postApiService.getUser(any())).thenThrow(mock<PostApiException.InvalidRequestError>())

            subject.getUser(id = 1)
        }

    @Test(expected = PostApiException.ParsingError::class)
    fun `when getUser is performed and fails to parse a response, should throw an exception`() =
        runBlockingTest {
            whenever(postApiService.getUser(any())).thenThrow(mock<PostApiException.ParsingError>())

            subject.getUser(id = 1)
        }

    @Test
    fun `when getUser is performed and successful parse a response, should make a call with proper values`() =
        runBlockingTest {
            val userResponse = User(
                id = 1,
                name = "name",
                username = "username",
            )
            whenever(postApiService.getUser(id = 1)).thenReturn(userResponse)

            val actual = subject.getUser(id = 1)

            assertThat(actual.id).isEqualTo(1)
            assertThat(actual.name).isEqualTo("name")
            assertThat(actual.username).isEqualTo("username")
        }
    //endregion
}