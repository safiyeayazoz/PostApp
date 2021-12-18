package com.example.postapp.model


data class PostDetail(
    val userId: Int,
    val title: String,
    val body: String,
    val userName: String,
    val postOwner: String,
)
