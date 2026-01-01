package com.example.newsapp.ui.countrynews

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.newsapp.NewsApplication
import com.example.newsapp.R
import com.example.newsapp.data.model.Code
import com.example.newsapp.databinding.ActivityCountryDisplayBinding
import com.example.newsapp.di.component.DaggerActivityComponent
import com.example.newsapp.di.module.ActivityModule
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.displayErrorMessage
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryDisplayActivity : AppCompatActivity() {

    lateinit var binding: ActivityCountryDisplayBinding

    @Inject
    lateinit var viewModel: CountryNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_country_display)
        binding = ActivityCountryDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            setObserver()
        }
    }

    private suspend fun setObserver() {
        viewModel.getCountriesList()
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiStateCountry.collect {
                when (it) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        displayErrorMessage(it.message)
                    }

                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.chipGroup.removeAllViews()

                        it.data.forEach { item ->
                            addChipCountry(item)
                        }
                    }
                }
            }
        }
    }


    private fun addChipCountry(code: Code) {
        val chip = Chip(this)
        val country = code.value.capitalizeWords()
        chip.text = country
        chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        chip.setTypeface(null, Typeface.BOLD)
        chip.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.chipGroup.addView(chip)
        chip.setOnClickListener {
            startActivity(CountryNewsActivity.getStartIntent(this, code))
        }
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}