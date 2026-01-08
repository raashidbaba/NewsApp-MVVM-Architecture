package com.example.newsapp.ui.homescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.categorynews.CategoryDisplayActivity
import com.example.newsapp.ui.countrynews.CountryDisplayActivity
import com.example.newsapp.ui.languageNews.LanguageDisplayActivity
import com.example.newsapp.ui.offlineTopHeadlines.OfflineTopHeadlinesActivity
import com.example.newsapp.ui.topheadline.TopHeadlineActivity
import com.example.newsapp.ui.topheadlinepagination.TopHeadlinePaginationActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topHeadlinesButton.setOnClickListener {
            val intent = Intent(this, TopHeadlineActivity::class.java)
            startActivity(intent)
        }

        binding.paginationTopHeadlinesButton.setOnClickListener {
            val intent = Intent(this, TopHeadlinePaginationActivity::class.java)
            startActivity(intent)
        }
        binding.newsSourcesButton.setOnClickListener {
            val intent = Intent(this, CategoryDisplayActivity::class.java)
            startActivity(intent)
        }

        binding.countrySelectionButton.setOnClickListener {
            val intent = Intent(this,CountryDisplayActivity::class.java)
            startActivity(intent)
        }

        binding.languageSelectionButton.setOnClickListener {
            val intent = Intent(this, LanguageDisplayActivity::class.java)
            startActivity(intent)
        }
        binding.offlineTopHeadlinesButton.setOnClickListener {
            val intent = Intent(this, OfflineTopHeadlinesActivity::class.java)
            startActivity(intent)
        }
    }
}