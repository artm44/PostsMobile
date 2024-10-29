package com.artm44.postsmobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.artm44.postsmobile.R
import com.artm44.postsmobile.ui.components.CommentItem
import com.artm44.postsmobile.ui.components.PostItem
import com.artm44.postsmobile.viewmodel.MainViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    // Определяем состояние, когда комментарии открыты
    val areCommentsVisible = viewModel.selectedPostId != null && viewModel.comments.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Кнопка загрузки постов или возврата назад
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(40.dp)
                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.large)
        ) {
            IconButton(
                onClick = {
                    if (areCommentsVisible) {
                        viewModel.selectedPostId = null // Возврат к постам
                    } else {
                        viewModel.loadPosts() // Загрузка постов
                    }
                }
            ) {
                Icon(
                    imageVector = if (areCommentsVisible) Icons.AutoMirrored.Filled.ArrowBack else Icons.Rounded.Refresh,
                    contentDescription = stringResource(if (areCommentsVisible) R.string.back_to_posts else R.string.update_posts)
                )
            }
        }


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (areCommentsVisible) {
                val selectedPost = viewModel.posts.find { it.id == viewModel.selectedPostId }
                if (selectedPost != null) {
                    item {
                        PostItem(selectedPost) {
                        }
                    }

                    item {
                        Text(
                            text = stringResource(R.string.comments),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    items(viewModel.comments) { comment ->
                        CommentItem(comment)
                    }
                }
            } else {
                items(viewModel.posts) { post ->
                    PostItem(post) {
                        viewModel.loadComments(post.id)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
