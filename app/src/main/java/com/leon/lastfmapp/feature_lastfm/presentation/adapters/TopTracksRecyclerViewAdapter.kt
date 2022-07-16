package com.leon.lastfmapp.feature_lastfm.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leon.lastfmapp.databinding.TopTracksItemBinding
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject

class TopTracksRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<TopTracksRecyclerViewAdapter.ViewHolder>()
{
    
    inner class ViewHolder(val binding: TopTracksItemBinding) : RecyclerView.ViewHolder(binding.root)
    
    var topTracks = listOf<Track>()
        private set
    
    
    suspend fun updateDataset(newDataset: List<Track>) = withContext(Dispatchers.Default) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback()
        {
            override fun getOldListSize(): Int
            {
                return topTracks.size
            }
            
            override fun getNewListSize(): Int
            {
                return newDataset.size
            }
            
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return topTracks[oldItemPosition] == newDataset[newItemPosition]
            }
            
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return topTracks[oldItemPosition] == newDataset[newItemPosition]
            }
        })
        
        withContext(Dispatchers.Main) {
            
            topTracks = newDataset
            diff.dispatchUpdatesTo(this@TopTracksRecyclerViewAdapter)
        }
        
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopTracksRecyclerViewAdapter.ViewHolder
    {
        return ViewHolder(TopTracksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: TopTracksRecyclerViewAdapter.ViewHolder, position: Int)
    {
        with(holder) {
            with(topTracks[position]) {
                
                Glide.with(binding.albumIconImageView.context)
                    .load(this.image[1].text)
                    .into(binding.albumIconImageView)
                
                binding.trackNameTextView.text = this.name
                binding.artistNameTextView.text = this.artist.name
                
                val df2 = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()))
                binding.playCountTextView.text = df2.format(this.playcount.toInt())
                binding.listenersCountTextView.text = df2.format(this.listeners.toInt())
            }
        }
    }
    
    override fun getItemCount(): Int
    {
        return topTracks.size
    }
}