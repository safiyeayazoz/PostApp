package com.example.postapp.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postapp.model.Post
import com.example.postapp.repository.PostRepository
import com.example.postapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
@HiltViewModel
class PostListViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    val posts: MutableState<List<Post>> = mutableStateOf(ArrayList())

    val loading = mutableStateOf(false)

    // Request starts at '0' (-1 = exhausted)
    val start = mutableStateOf(START_POSITION)

    private var postListScrollPosition = 0


    init{
       viewModelScope.launch {
           getPosts()
       }
    }

    private suspend fun getPosts(){
           loading.value = true
            val postList = postRepository.getPostList(start = START_POSITION)
            posts.value = postList
            loading.value = false
    }

    /**
     * Get new posts from api
     */
    internal fun nextPosts() {
        try {
            viewModelScope.launch {
                if ((postListScrollPosition + 1) >= (start.value) && start.value <= MAX_POST_SIZE) {
                    loading.value = true
                    incrementStartValue()

                    if (start.value > QUERY_ITEM_SIZE) {
                        withContext(Dispatchers.IO) {
                            val result = postRepository.getPostList(start = start.value)
                            appendPosts(result)
                        }
                    }

                    loading.value = false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "nextPost(): ${e.cause}")
        }
    }

    /**
     * Append new posts to the current list of pots
     */
    private fun appendPosts(posts: List<Post>) {
        val current = ArrayList(this.posts.value)
        current.addAll(posts)
        this.posts.value = current
    }

    /**
     * Increment of start value for next iteration api call
     */
    private fun incrementStartValue() {
        start.value = start.value + QUERY_ITEM_SIZE
    }

    /**
     * It uses to understand users post position
     */
    fun onChangePostScrollPosition(position: Int) {
        postListScrollPosition = position
    }

    companion object {
        const val QUERY_ITEM_SIZE = 10 // The size of how many posts will get in the response for each requests
        const val MAX_POST_SIZE = 100 // Api includes 100 posts
        const val START_POSITION = 0  // Start of scroll position and request item index
    }
}
