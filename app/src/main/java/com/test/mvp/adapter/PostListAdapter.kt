package com.test.mvp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.mvp.databinding.PostCellBinding
import com.test.mvp.network.model.PostDTO
import javax.inject.Inject

class PostListAdapter @Inject constructor(): RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    private var list = listOf<PostDTO>()

    fun addItems(newItems : List<PostDTO>){
        list = newItems
        notifyDataSetChanged()
    }

    inner class PostViewHolder(val postCellBinding: PostCellBinding) : RecyclerView.ViewHolder(postCellBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListAdapter.PostViewHolder {
        val binding = PostCellBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListAdapter.PostViewHolder, position: Int) {
        holder.postCellBinding.apply {
            val post = list[position]
            tvUserId.text = post.userId.toString()
            tvTitle.text = post.title
            tvBody.text = post.body
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}