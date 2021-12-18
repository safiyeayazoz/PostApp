package com.example.postapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.postapp.R
import com.example.postapp.model.Post

@Composable
fun PostCard(
    post: Post,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = post.title,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h6,
                    color = colorResource(R.color.post_app_green),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                post.postUiBody?.let {
                    Text(
                        text = post.postUiBody,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}