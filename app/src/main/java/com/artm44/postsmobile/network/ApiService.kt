package com.artm44.postsmobile.network

import com.artm44.postsmobile.models.Comment
import com.artm44.postsmobile.models.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): Response<List<Comment>>
}