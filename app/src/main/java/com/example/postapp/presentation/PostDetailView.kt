package com.example.postapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.postapp.R
import com.example.postapp.model.PostDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PostDetailView(
    post: PostDetail,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ) {
                    Text(
                        text = post.title,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h6,
                        color = colorResource(R.color.post_app_green),
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 16.dp,
                            bottom = 16.dp,
                        )
                ) {
                    Text(
                        text = post.body,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.body1
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            start = 8.dp,
                        )
                ) {
                    Text(
                        text = "Created by ${post.postOwner}",
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 8.dp,
                        )
                ) {
                    Text(
                        text = "@" + post.userName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.subtitle1,
                        color = colorResource(R.color.post_app_green)
                    )
                }
            }
        }
    }
}