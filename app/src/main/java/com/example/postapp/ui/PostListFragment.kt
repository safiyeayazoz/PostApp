package com.example.postapp.ui

import PostList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.postapp.presentation.PostCard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.navigation.findNavController
import com.example.postapp.R

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostListFragment : Fragment() {
    private val viewModel: PostListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val posts = viewModel.posts.value
                val start = viewModel.start.value
                val loading = viewModel.loading.value

                LazyColumn {
                    itemsIndexed(
                        items = posts
                    ) { index, post ->
                        viewModel.onChangePostScrollPosition(index)
                        if ((index + 1) >= (start) && !loading) {
                            viewModel.nextPosts()
                        }
                        PostCard(post = post, onClick = {})
                    }
                }

                PostList(
                    loading = loading,
                    posts = posts,
                    start = start,
                    onChangeScrollPosition = viewModel::onChangePostScrollPosition,
                    onTriggerNextPosts = { viewModel.nextPosts() },
                    onNavigateToPostDetailScreen = {
                        val bundle = Bundle()
                        bundle.putString("postTitle", it.title)
                        bundle.putString("postBody", it.body)
                        bundle.putInt("postUserId", it.userId)
                        findNavController().navigate(R.id.action_to_postDetail, bundle)
                    }
                )
            }

        }
    }
}