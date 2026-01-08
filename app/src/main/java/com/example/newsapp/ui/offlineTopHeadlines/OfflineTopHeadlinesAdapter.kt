package com.example.newsapp.ui.offlineTopHeadlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.local.entity.Source
import com.example.newsapp.databinding.TopheadlinesSourcesItemBinding
import com.example.newsapp.utils.ItemClickListener
import com.example.newsapp.utils.capitalizeWords
import com.example.newsapp.utils.getCountryName
import com.example.newsapp.utils.getLanguageName

class OfflineTopHeadlinesAdapter(private val sourceList: ArrayList<Source>) :
    RecyclerView.Adapter<OfflineTopHeadlinesAdapter.DataViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<Source>

    class DataViewHolder(private val binding: TopheadlinesSourcesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: Source, itemClickListener: ItemClickListener<Source>) {
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


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
       return DataViewHolder(
           TopheadlinesSourcesItemBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
           )
       )
    }

    override fun onBindViewHolder(
        holder: DataViewHolder,
        position: Int
    ) {
        holder.bind(sourceList[position], itemClickListener =itemClickListener)
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    fun addData(list: List<Source>){
        sourceList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData(){
        sourceList.clear()
    }
}