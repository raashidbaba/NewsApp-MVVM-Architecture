package com.example.newsapp.ui.offlineTopHeadlines

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NewsApplication
import com.example.newsapp.data.local.entity.Source
import com.example.newsapp.databinding.ActivityOfflineTopHeadlinesBinding
import com.example.newsapp.di.component.DaggerActivityComponent
import com.example.newsapp.di.module.ActivityModule
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.displayErrorMessage
import com.example.newsapp.utils.launchCustomTab
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfflineTopHeadlinesActivity : AppCompatActivity() {

    lateinit var binding: ActivityOfflineTopHeadlinesBinding

    @Inject
    lateinit var adapter: OfflineTopHeadlinesAdapter

    @Inject
    lateinit var viewModel: OfflineTopHeadlinesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfflineTopHeadlinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependecies()
        setupRecyclerUi()
        lifecycleScope.launch {
            setUpObserver()
        }
    }


    private suspend fun setUpObserver() {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect {
                when (it) {
                    is UiState.Loading -> {
                        binding.progressBarOfflineTopHeadlines.visibility = View.VISIBLE
                        binding.recyclerViewOfflineTopHeadlines.visibility = View.GONE
                    }

                    is UiState.Success -> {
                        binding.progressBarOfflineTopHeadlines.visibility = View.GONE
                        binding.recyclerViewOfflineTopHeadlines.visibility = View.VISIBLE
                        setupRecyclerData(it.data)
                    }

                    is UiState.Error -> {
                        binding.progressBarOfflineTopHeadlines.visibility = View.GONE
                        binding.errorMsgOfflineTopHeadlines.visibility = View.VISIBLE
                        displayErrorMessage(it.message)
                    }
                }
            }
        }
    }


    private fun setupRecyclerUi() {
        val recyclerView = binding.recyclerViewOfflineTopHeadlines
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


    private fun setupRecyclerData(data: List<Source>) {
        adapter.clearData()
        adapter.addData(data)
    }

    private fun injectDependecies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }


}