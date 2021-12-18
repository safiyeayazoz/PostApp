package com.example.postapp.util

import com.example.postapp.model.Post

internal fun List<Post>.updatePostList(): List<Post>{
    return this.map { post ->
        post.copy(
            id = post.id,
            userId = post.userId,
            title = post.title,
            body = post.body.replace("\n", " ", false),
            postUiBody = post.body.take(120).replace("\n", " ", false) + "...",
        )
    }
}
