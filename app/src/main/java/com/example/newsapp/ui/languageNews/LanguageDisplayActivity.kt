package com.example.newsapp.ui.languageNews

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.newsapp.NewsApplication
import com.example.newsapp.R
import com.example.newsapp.data.model.Code
import com.example.newsapp.databinding.ActivityLanguageDisplayBinding
import com.example.newsapp.di.component.DaggerActivityComponent
import com.example.newsapp.di.module.ActivityModule
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.displayErrorMessage
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageDisplayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageDisplayBinding

    @Inject
    lateinit var viewModel: LanguageNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependencies()
        lifecycleScope.launch {
            setUpObserver()
        }
    }

    private suspend fun setUpObserver() {
        viewModel.getLanguageList()
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiStateLanguage.collect {
                when (it) {
                    UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is UiState.Success -> {
                        binding.chipGroup.removeAllViews()
                        binding.progressBar.visibility = View.GONE
                        binding.languageTitleTV.visibility = View.VISIBLE
                        for (item in it.data){
                            addChipLanguage(item)
                        }
                    }

                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        displayErrorMessage(it.message)
                    }
                }
            }

        }
    }

    private fun addChipLanguage(code: Code) {
        val chip = Chip(this)
        val language = code.value.capitalizeWords()
        chip.text = language
        chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        chip.setTypeface(null, Typeface.BOLD)
        chip.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.chipGroup.addView(chip)
        chip.setOnClickListener {
            startActivity(LanguageNewsActivity.getStartIntent(this, code))
        }
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}