package com.example.newsapp.ui.languageNews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.databinding.TopheadlinesSourcesItemBinding
import com.example.newsapp.utils.ItemClickListener
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.getCountryName
import com.example.newsapp.utils.getLanguageName

class LanguageNewsAdapter(private val newsList: ArrayList<ApiSource>) :
    RecyclerView.Adapter<LanguageNewsAdapter.ViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<ApiSource>

    class ViewHolder(private val binding: TopheadlinesSourcesItemBinding) :
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            TopheadlinesSourcesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(newsList[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun addData(list:List<ApiSource>){
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData(){
        newsList.clear()
    }


}