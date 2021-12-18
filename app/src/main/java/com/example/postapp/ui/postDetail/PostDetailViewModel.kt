package com.example.postapp.ui.postDetail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postapp.model.User
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
class PostDetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val state: SavedStateHandle,
) : ViewModel() {

    val user: MutableState<User> = mutableStateOf(User(-1, "", ""))

    init {
        // restore if process dies
        state.get<Int>(STATE_KEY_POST)?.let { postId ->
            onTriggerEvent(GetUserEvent(postId))
        }
    }

    fun onTriggerEvent(event: GetUserEvent) {
        try {
            if (user.value.name.isEmpty()) {
                getUser(event.id)
            }
        } catch (e: Exception) {
            Log.e(TAG, "onTriggerEvent(event: $event):  ${e.cause}")
        }
    }

    private fun getUser(id: Int) {
        viewModelScope.launch {
            val postUser: User
            withContext(Dispatchers.IO) {
                postUser = postRepository.getUser(id = id)
            }
            user.value = postUser

            state.set(STATE_KEY_POST, postUser.id)
        }
    }

    companion object {
        const val STATE_KEY_POST = "post.state.detail.key"
    }
}