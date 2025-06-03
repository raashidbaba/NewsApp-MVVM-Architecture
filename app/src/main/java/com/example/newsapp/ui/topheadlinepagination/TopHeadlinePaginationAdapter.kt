package com.example.newsapp.ui.topheadlinepagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.databinding.TopheadlinesSourcesItemBinding
import com.example.newsapp.utils.ItemClickListener
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.getCountryName
import com.example.newsapp.utils.getLanguageName

class TopHeadlinePaginationAdapter() :
    PagingDataAdapter<ApiSource, TopHeadlinePaginationAdapter.DataViewHolder>(DIFF_CALLBACK) {

    lateinit var itemClickListener: ItemClickListener<ApiSource>

    inner class DataViewHolder(private val binding: TopheadlinesSourcesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(apiSource: ApiSource, itemClickListener: ItemClickListener<ApiSource>) {
            binding.sourceNameTV.text = apiSource.name
            binding.sourceDescriptionTV.text = apiSource.description
            binding.categoryTV.text = apiSource.category.capitalizeWords()
            binding.countryTV.text = apiSource.country.getCountryName(apiSource.country)
            binding.languageTV.text = apiSource.language.getLanguageName(apiSource.language)
            itemView.setOnClickListener {
                itemClickListener(apiSource)
            }
        }
    }

    override fun onBindViewHolder(
        holder: TopHeadlinePaginationAdapter.DataViewHolder,
        position: Int
    ) {
        val source = getItem(position)
        if (source != null) {
            holder.bind(source, itemClickListener)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopHeadlinePaginationAdapter.DataViewHolder {
        return DataViewHolder(
            TopheadlinesSourcesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ApiSource>() {
            override fun areItemsTheSame(oldItem: ApiSource, newItem: ApiSource): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ApiSource, newItem: ApiSource): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}