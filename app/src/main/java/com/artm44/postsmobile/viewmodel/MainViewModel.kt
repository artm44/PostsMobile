package com.artm44.postsmobile.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artm44.postsmobile.models.Comment
import com.artm44.postsmobile.models.Post
import com.artm44.postsmobile.network.RetrofitInstance
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    var posts by mutableStateOf<List<Post>>(emptyList())
        private set
    var comments by mutableStateOf<List<Comment>>(emptyList())
        private set
    var selectedPostId by mutableStateOf<Int?>(null)

    fun getPosts() {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getPosts()
            if (response.isSuccessful) {
                posts = response.body() ?: emptyList()
            }
        }
    }

    fun getComments(postId: Int) {
        viewModelScope.launch {
            selectedPostId = postId
            val response = RetrofitInstance.api.getComments(postId)
            if (response.isSuccessful) {
                comments = response.body() ?: emptyList()
            }
        }
    }
}
