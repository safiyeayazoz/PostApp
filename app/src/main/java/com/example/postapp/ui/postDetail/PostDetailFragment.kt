package com.example.postapp.ui.postDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.postapp.model.PostDetail
import com.example.postapp.presentation.NothingHere
import com.example.postapp.presentation.PostDetailView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private var userId: Int? = null
    private var postTitle: String? = null
    private var postBody: String? = null
    private lateinit var postDetail: PostDetail

    private val viewModel: PostDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postTitle = it.getString("postTitle")
            postBody = it.getString("postBody")
            userId = it.getInt("postUserId")

            userId?.let { id ->
                viewModel.onTriggerEvent(GetUserEvent(id))
            }


        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val user = viewModel.user.value

                postTitle?.let { title ->
                    postBody?.let { body ->
                        userId?.let {
                            postDetail = PostDetail(
                                userId = it,
                                title = title,
                                body = body,
                                userName = user.username,
                                postOwner = user.name,
                            )
                        }
                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.surface)
                ) {
                    if (postTitle.isNullOrEmpty() || postBody.isNullOrEmpty()) {
                        NothingHere()
                    } else {
                        PostDetailView(
                            post = postDetail
                        )
                    }
                }
            }
        }
    }
}