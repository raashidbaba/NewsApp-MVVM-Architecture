package com.example.newsapp.ui.categorynews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Video.VideoColumns.CATEGORY
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NewsApplication
import com.example.newsapp.R
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.databinding.ActivityCategoryNewsBinding
import com.example.newsapp.di.component.DaggerActivityComponent
import com.example.newsapp.di.module.ActivityModule
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.displayErrorMessage
import com.example.newsapp.utils.launchCustomTab
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryNewsBinding

    @Inject
    lateinit var viewModel: CategoryNewsViewModel

    @Inject
    lateinit var adapter: CategoryNewsAdapter


    companion object {
        fun getStartIntent(context: Context, category: String): Intent {
            return Intent(context, CategoryNewsActivity::class.java).apply {
                putExtra(CATEGORY, category)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryValue = intent.getStringExtra(CATEGORY) ?: ""
        binding.titleTV.text = getString(
            R.string.news_categories_headline,
            categoryValue.capitalizeWords()
        )
        setUpRecyclerView()
        lifecycleScope.launch {
            setObserver(categoryValue)
        }
    }

    private suspend fun setObserver(category: String) {
        viewModel.getNewsFromCategories(category)
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiStateCategoryNews.collect {
                when (it) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }

                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorMsgTV.visibility = View.VISIBLE
                        displayErrorMessage(it.message)
                    }

                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorMsgTV.visibility = View.GONE
                        if (it.data.isEmpty()) {
                            binding.noDataDisplayTV.visibility = View.VISIBLE
                        } else {
                            binding.recyclerView.visibility = View.VISIBLE
                            setUpRecyclerData(it.data)
                        }
                    }
                }
            }
        }
    }


    private fun setUpRecyclerData(list: List<ApiSource>) {
        adapter.clearData()
        adapter.addData(list)
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
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

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}