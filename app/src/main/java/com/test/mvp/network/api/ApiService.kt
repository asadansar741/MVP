package com.test.mvp.network.api

import com.test.mvp.network.model.PostDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getPosts(
        @Query("userId") userId:Int
    ):Response<List<PostDTO>>

}