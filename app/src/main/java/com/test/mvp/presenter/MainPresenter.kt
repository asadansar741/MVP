package com.test.mvp.presenter

import com.test.mvp.contracts.MainActivityContract
import com.test.mvp.network.model.PostDTO
import com.test.mvp.util.launchOnMain
import kotlinx.coroutines.*

class MainPresenter (
    private val view : MainActivityContract.View,
    private val model: MainActivityContract.Model
): MainActivityContract.Presenter, MainActivityContract.Model.OnFinishListener {

    private val scope = CoroutineScope(Dispatchers.IO)
    override fun getPosts(userId: Int) {
        scope.launch {
            model.fetchPosts(onFinishListener = this@MainPresenter, userId)
        }
    }

    override fun onDestroy() {
        scope.cancel()
    }

    override fun onLoading() {
        scope.launchOnMain {
            view.onLoading()
        }
    }

    override fun onError(message: String) {
        scope.launchOnMain {
            view.onError(message)
        }
    }

    override fun onSuccess(list: List<PostDTO>) {
        scope.launchOnMain {
            view.onSuccess(list)
        }
    }
}