package com.example.newsapp.ui.languageNews

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.example.newsapp.data.model.Code
import com.example.newsapp.databinding.ActivityLanguageNewsBinding
import com.example.newsapp.di.component.DaggerActivityComponent
import com.example.newsapp.di.module.ActivityModule
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.AppConstant
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.displayErrorMessage
import com.example.newsapp.utils.launchCustomTab
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageNewsBinding

    @Inject
     lateinit var viewModel: LanguageNewsViewModel

    @Inject
    lateinit var adapter: LanguageNewsAdapter

    companion object{
        fun getStartIntent(context: Context,code: Code): Intent{
            return Intent(context, LanguageNewsActivity::class.java).apply {
                putExtra(AppConstant.LANGUAGE,code.value)
                putExtra(AppConstant.CODE,code.id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependencies()
        val languageValue = intent.getStringExtra(AppConstant.LANGUAGE)
        val languageCode = intent.getStringExtra(AppConstant.CODE)
        if (languageValue != null) {
            binding.titleTV.text = getString(
                R.string.news_languages_headline,
                languageValue.capitalizeWords()
            )
        }
        lifecycleScope.launch {
            if (languageValue != null && languageCode != null) {
                val code = Code(languageCode, languageValue)
                setUpObserver(code)
            }
        }
        setUpRecyclerView()

    }

    private suspend fun setUpObserver(code: Code) {
        viewModel.getLanguageBasedNews(code.id)
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiStateLanguageNews.collect {
                when (it) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }

                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorMsgTV.visibility = View.GONE
                        if (it.data.isEmpty()) {
                            binding.noDataDisplayTV.text =
                                getString(R.string.language_news_empty_display_text, code.value)
                        } else {
                            binding.recyclerView.visibility = View.VISIBLE
                            setUpRecyclerData(it.data)
                        }
                    }

                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorMsgTV.visibility = View.VISIBLE
                        displayErrorMessage(it.message)
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