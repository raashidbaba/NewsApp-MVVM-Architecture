package com.example.newsapp.ui.countrynews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NewsApplication
import com.example.newsapp.R
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.model.Code
import com.example.newsapp.databinding.ActivityCountryNewsBinding
import com.example.newsapp.di.component.DaggerActivityComponent
import com.example.newsapp.di.module.ActivityModule
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.AppConstant
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.displayErrorMessage
import com.example.newsapp.utils.launchCustomTab
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryNewsBinding

    @Inject
    lateinit var viewModel: CountryNewsViewModel

    @Inject
    lateinit var adapter: CountryNewsAdapter

    companion object {
        fun getStartIntent(context: Context, country: Code): Intent {
            return Intent(context, CountryNewsActivity::class.java).apply {
                putExtra(AppConstant.COUNTRY, country.value)
                putExtra(AppConstant.CODE, country.id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCountryNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        val countryValue = intent.getStringExtra(AppConstant.COUNTRY)
        val countryCode = intent.getStringExtra(AppConstant.CODE)
        if (countryValue != null) {
            binding.titleTV.text = getString(
                R.string.news_countries_headline,
                countryValue.capitalizeWords()
            )
        }

        lifecycleScope.launch {
            if (countryCode != null) {
                setObserver(countryCode)
            }
        }
    }


    private suspend fun setObserver(value: String) {
        viewModel.getCountryNews(value)
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiStateNews.collect {
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

    private fun setUpRecyclerData(data: List<ApiSource>) {
        adapter.clearData()
        adapter.addData(data)
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