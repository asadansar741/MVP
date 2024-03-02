package com.test.mvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import com.test.mvp.adapter.PostListAdapter
import com.test.mvp.contracts.MainActivityContract
import com.test.mvp.databinding.ActivityMainBinding
import com.test.mvp.model.MainModel
import com.test.mvp.network.model.PostDTO
import com.test.mvp.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , MainActivityContract.View{

    private var _binding: ActivityMainBinding?= null
    private val binding: ActivityMainBinding
    get() = _binding!!

    @Inject
    lateinit var postListAdapter: PostListAdapter

    @Inject
    lateinit var mainModel: MainModel
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(this,mainModel)
        initViews()
    }

    override fun onLoading() {
        binding.progressBar.isVisible = true
    }

    override fun onSuccess(list: List<PostDTO>) {
        binding.progressBar.isVisible = false
        postListAdapter.addItems(list)
        binding.recyclerView.isVisible = true
    }

    override fun onError(message: String) {
        binding.progressBar.isVisible = false
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun initViews(){
        binding.apply {
            recyclerView.adapter = postListAdapter
            searchView.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isNotEmpty() == true){
                        val userId = newText.toInt()
                        presenter.getPosts(userId)
                    }
                    return true
                }
            })
        }
    }
}