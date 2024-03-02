package com.test.mvp.contracts

import com.test.mvp.network.model.PostDTO

interface MainActivityContract {

    interface View{
        fun onLoading()
        fun onSuccess(list:List<PostDTO>)
        fun onError(message:String)
    }

    interface Presenter{
        fun getPosts(userId:Int)
        fun onDestroy()
    }

    interface Model{
        interface OnFinishListener{
            fun onLoading()
            fun onError(message:String)
            fun onSuccess(list:List<PostDTO>)
        }
        suspend fun fetchPosts(onFinishListener: OnFinishListener, userId: Int)
    }
}