package com.example.newsapp.ui.topheadlinepagination

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NewsApplication
import com.example.newsapp.databinding.ActivityTopHeadlinePaginationBinding
import com.example.newsapp.di.component.DaggerActivityComponent
import com.example.newsapp.di.module.ActivityModule
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.displayErrorMessage
import com.example.newsapp.utils.launchCustomTab
import kotlinx.coroutines.launch
import javax.inject.Inject


class TopHeadlinePaginationActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: TopHeadlinePaginationViewModel

    @Inject
    lateinit var adapter: TopHeadlinePaginationAdapter

    private lateinit var binding: ActivityTopHeadlinePaginationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlinePaginationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependencies()
        setObserver()
        setUi()

    }

    private fun setUi() {
        val recyclerView = binding.recyclerViewPagingTopHeadlines
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
        adapter.itemClickListener = {
            launchCustomTab(it.url)
        }
    }


    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Loading -> {
                            binding.progressBarPagingTopHeadlines.visibility = View.VISIBLE
                            binding.recyclerViewPagingTopHeadlines.visibility = View.GONE
                        }

                        is UiState.Success -> {
                            binding.progressBarPagingTopHeadlines.visibility = View.GONE
                            binding.recyclerViewPagingTopHeadlines.visibility = View.VISIBLE
                            adapter.submitData(it.data)
                        }

                        is UiState.Error -> {
                            binding.progressBarPagingTopHeadlines.visibility = View.GONE
                            binding.errorMsgPagingTopHeadlines.visibility = View.VISIBLE
                            displayErrorMessage(it.message)
                        }
                    }
                }
            }
        }
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}

