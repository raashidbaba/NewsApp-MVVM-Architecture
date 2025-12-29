package com.example.newsapp.ui.countrynews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.databinding.TopheadlinesSourcesItemBinding
import com.example.newsapp.utils.ItemClickListener
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.getCountryName
import com.example.newsapp.utils.getLanguageName

class CountryNewsAdapter(private val newsList: ArrayList<ApiSource>) :
    RecyclerView.Adapter<CountryNewsAdapter.CountryNewsViewHolder>() {
    lateinit var itemClickListener: ItemClickListener<ApiSource>

    class CountryNewsViewHolder(private val binding: TopheadlinesSourcesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: ApiSource, itemClickListener: ItemClickListener<ApiSource>) {
            binding.sourceNameTV.text = source.name
            binding.sourceDescriptionTV.text = source.description
            binding.categoryTV.text = source.category.capitalizeWords()
            binding.languageTV.text = source.language.getLanguageName(source.language)
            binding.countryTV.text = source.country.getCountryName(source.country)
            itemView.setOnClickListener {
                itemClickListener(source)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryNewsViewHolder {
        return CountryNewsViewHolder(
            TopheadlinesSourcesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: CountryNewsViewHolder, position: Int) {
        holder.bind(newsList[position],itemClickListener)
    }

    fun clearData(){
        newsList.clear()
    }

    fun addData(list: List<ApiSource>){
        newsList.addAll(list)
        notifyDataSetChanged()
    }
}