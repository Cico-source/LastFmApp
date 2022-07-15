package com.leon.lastfmapp.feature_lastfm.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leon.lastfmapp.databinding.SearchedArtistsItemBinding
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchedArtistsRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<SearchedArtistsRecyclerViewAdapter.ViewHolder>()
{
    
    inner class ViewHolder(val binding: SearchedArtistsItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        
        init
        {
            binding.cardLayout.setOnClickListener {
                
                onItemClickListener?.let { click ->
                    
                    click(searchedArtists[adapterPosition].name)
                }
            }
        }
        
    }
    
    var searchedArtists = listOf<Artist>()
        private set
    
    private var onItemClickListener: ((String) -> Unit)? = null
    
    
    fun setOnItemClickListener(listener: (String) -> Unit)
    {
        onItemClickListener = listener
    }
    
    suspend fun updateDataset(newDataset: List<Artist>) = withContext(Dispatchers.Default) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback()
        {
            override fun getOldListSize(): Int
            {
                return searchedArtists.size
            }
            
            override fun getNewListSize(): Int
            {
                return newDataset.size
            }
            
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return searchedArtists[oldItemPosition] == newDataset[newItemPosition]
            }
            
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return searchedArtists[oldItemPosition] == newDataset[newItemPosition]
            }
        })
        
        withContext(Dispatchers.Main) {
            
            searchedArtists = newDataset
            diff.dispatchUpdatesTo(this@SearchedArtistsRecyclerViewAdapter)
        }
        
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedArtistsRecyclerViewAdapter.ViewHolder
    {
        return ViewHolder(SearchedArtistsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: SearchedArtistsRecyclerViewAdapter.ViewHolder, position: Int)
    {
        with(holder) {
            with(searchedArtists[position]) {
                
                binding.artistNameTextView.text = this.name
                
            }
        }
    }
    
    override fun getItemCount(): Int
    {
        return searchedArtists.size
    }
}