package com.leon.lastfmapp.feature_lastfm.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leon.lastfmapp.databinding.TopArtistsItemBinding
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject

class TopArtistsRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<TopArtistsRecyclerViewAdapter.ViewHolder>()
{
    
    inner class ViewHolder(val binding: TopArtistsItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        
        init
        {
            binding.cardLayout.setOnClickListener {

                onItemClickListener?.let { click ->

                    click(topArtists[adapterPosition].name)
                }
            }
        }
        
    }
    
    var topArtists = listOf<Artist>()
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
                return topArtists.size
            }
            
            override fun getNewListSize(): Int
            {
                return newDataset.size
            }
            
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return topArtists[oldItemPosition] == newDataset[newItemPosition]
            }
            
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return topArtists[oldItemPosition] == newDataset[newItemPosition]
            }
        })
        
        withContext(Dispatchers.Main) {
            
            topArtists = newDataset
            diff.dispatchUpdatesTo(this@TopArtistsRecyclerViewAdapter)
        }
        
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopArtistsRecyclerViewAdapter.ViewHolder
    {
        return ViewHolder(TopArtistsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: TopArtistsRecyclerViewAdapter.ViewHolder, position: Int)
    {
        with(holder) {
            with(topArtists[position]) {
                
                Glide.with(binding.albumIconImageView.context)
                    .load(this.image[1].text)
                    .into(binding.albumIconImageView)
                
                binding.artistNameTextView.text = this.name
                
                val df2 = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()))
                binding.playCountTextView.text = df2.format(this.playcount.toInt())
                binding.listenersCountTextView.text = df2.format(this.listeners.toInt())
            }
        }
    }
    
    override fun getItemCount(): Int
    {
        return topArtists.size
    }
}