import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.postapp.model.Post
import com.example.postapp.presentation.PostCard
import com.example.postapp.presentation.ProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun PostList(
    loading: Boolean,
    posts: List<Post>,
    start: Int,
    onChangeScrollPosition: (Int) -> Unit,
    onTriggerNextPosts: () -> Unit,
    onNavigateToPostDetailScreen: (Post) -> Unit,

    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && posts.isEmpty()) {
            ProgressBar()
        } else {
            LazyColumn {
                itemsIndexed(
                    items = posts
                ) { index, post ->
                    onChangeScrollPosition(index)
                    if ((index + 1) >= start && !loading) {
                        onTriggerNextPosts()
                    }
                    PostCard(
                        post = post,
                        onClick = { onNavigateToPostDetailScreen(post) }
                    )
                }
            }
        }
    }
}