package com.example.newsapp.ui.categorynews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.databinding.CategoryNewsItemBinding
import com.example.newsapp.utils.ItemClickListener
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.getCountryName
import com.example.newsapp.utils.getLanguageName

class CategoryNewsAdapter(private val newsList: ArrayList<ApiSource>) :
    RecyclerView.Adapter<CategoryNewsAdapter.DataViewHolder>() {
    lateinit var itemClickListener: ItemClickListener<ApiSource>


    class DataViewHolder(private val binding: CategoryNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ApiSource, itemClickListener: ItemClickListener<ApiSource>) {
            binding.sourceNameTV.text = news.name
            binding.sourceDescriptionTV.text = news.description
            binding.categoryTV.text = news.category.capitalizeWords()
            binding.countryTV.text = news.country.getCountryName(news.country)
            binding.languageTV.text = news.language.getLanguageName(news.language)
            itemView.setOnClickListener {
                itemClickListener(news)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            CategoryNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(newsList[position], itemClickListener = itemClickListener)
    }

    fun addData(list: List<ApiSource>) {
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        newsList.clear()
    }
}