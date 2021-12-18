package com.example.postapp

import com.example.postapp.repository.PostRepository
import com.example.postapp.ui.PostListViewModel
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostListViewModelTest {

    @Mock
    private lateinit var postRepository: PostRepository

    private val mainThreadSurrogate = newSingleThreadContext("Main thread")

    private lateinit var subject: PostListViewModel

    private val postListScrollPosition = 0

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        subject = PostListViewModel(
            postRepository,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `when nextPost is performed successfully, (postListScrollPosition+1) value should be equal or greater than start value`(): Unit =
        runBlocking {
            launch(Dispatchers.Main) {
                assertTrue((postListScrollPosition + 1) >= (subject.start.value))
            }
        }

    @Test
    fun `when nextPost is performed successfully, start value should be equal or smaller than max post value`(): Unit =
        runBlocking {
            launch(Dispatchers.Main) {
                assertTrue(subject.start.value <= PostListViewModel.MAX_POST_SIZE)
            }
        }

    @Test
    fun `when nextPost is performed successfully, loading value should be false`(): Unit =
        runBlocking {
            launch(Dispatchers.Main) {
                subject.nextPosts()
            }

            assertFalse(subject.loading.value)
        }

    @Test
    fun `when nextPost is performed successfully, getPostList should call from repository`(): Unit =
        runBlocking {
            launch(Dispatchers.Main) {
                subject.nextPosts()
            }

            verify(
                postRepository,
                atLeastOnce()
            ).getPostList(start = PostListViewModel.START_POSITION)
        }
}