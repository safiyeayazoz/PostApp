package com.example.postapp

import com.example.postapp.model.Post
import com.example.postapp.util.updatePostList
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PostBodyToPostUiBodyExtTest {

    @Test
    fun `when updatePostList is called, should properly revert ui post body`() {
        val postList: List<Post> = listOf(
            Post(
                userId = 1,
                id = 2,
                title = "title",
                body = UUID.randomUUID().toString().repeat(5),
                postUiBody = null,
            )
        )

        val actual = postList.updatePostList()

        assertThat(actual[0].postUiBody?.length).isEqualTo(123)
        assertThat(actual[0].postUiBody).isNotNull
    }

    @Test
    fun `when updatePostList is called, should properly update post list items data`() {
        val postList: List<Post> = listOf(
            Post(
                userId = 1,
                id = 2,
                title = "title",
                body = "body\nbody",
                postUiBody = null,
            )
        )

        val actual = postList.updatePostList()

        assertThat(actual[0].userId).isEqualTo(1)
        assertThat(actual[0].id).isEqualTo(2)
        assertThat(actual[0].title).isEqualTo("title")
        assertThat(actual[0].body).isEqualTo("body body")
        assertThat(actual[0].postUiBody).isEqualTo("body body...")
    }
}