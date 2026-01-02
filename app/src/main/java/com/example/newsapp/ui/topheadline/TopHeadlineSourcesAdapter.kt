package com.example.newsapp.ui.topheadline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.databinding.TopheadlinesSourcesItemBinding
import com.example.newsapp.utils.ItemClickListener
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.getCountryName
import com.example.newsapp.utils.getLanguageName

class TopHeadlineSourcesAdapter(private val headlinesList: ArrayList<ApiSource>) :
    RecyclerView.Adapter<TopHeadlineSourcesAdapter.DataViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<ApiSource>

    class DataViewHolder(private val binding: TopheadlinesSourcesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: ApiSource, itemClickListener: ItemClickListener<ApiSource>) {
            binding.sourceNameTV.text = source.name
            binding.sourceDescriptionTV.text = source.description
            binding.categoryTV.text = source.category.capitalizeWords()
            binding.countryTV.text = source.country.getCountryName(source.country)
            binding.languageTV.text = source.language.getLanguageName(source.language)
            itemView.setOnClickListener {
                itemClickListener(source)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            TopheadlinesSourcesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return headlinesList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(headlinesList[position], itemClickListener)
    }

    fun addData(list: List<ApiSource>) {
        headlinesList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        headlinesList.clear()
    }
}