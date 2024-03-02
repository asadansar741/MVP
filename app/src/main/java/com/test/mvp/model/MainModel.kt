package com.test.mvp.model

import com.test.mvp.contracts.MainActivityContract
import com.test.mvp.network.api.ApiService
import javax.inject.Inject

class MainModel @Inject constructor(private val apiService: ApiService): MainActivityContract.Model {
    override suspend fun fetchPosts(
        onFinishListener: MainActivityContract.Model.OnFinishListener,
        userId: Int
    ) {
        onFinishListener.onLoading()
        try {
            val response = apiService.getPosts(userId)
            if (response.isSuccessful){
                response.body()?.let {
                    onFinishListener.onSuccess(it)
                }
            }
            else{
                onFinishListener.onError(response.errorBody().toString())
            }
        } catch (e:Exception){
            onFinishListener.onError(e.message.toString())
        }
    }
}