package com.leon.lastfmapp.feature_lastfm.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leon.lastfmapp.databinding.TopArtistTracksItemBinding
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject

class TopArtistTracksRecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<TopArtistTracksRecyclerViewAdapter.ViewHolder>()
{
    
    inner class ViewHolder(val binding: TopArtistTracksItemBinding) : RecyclerView.ViewHolder(binding.root)
    
    var topArtistTracks = listOf<Track>()
        private set
    
    
    suspend fun updateDataset(newDataset: List<Track>) = withContext(Dispatchers.Default) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback()
        {
            override fun getOldListSize(): Int
            {
                return topArtistTracks.size
            }
            
            override fun getNewListSize(): Int
            {
                return newDataset.size
            }
            
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return topArtistTracks[oldItemPosition] == newDataset[newItemPosition]
            }
            
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return topArtistTracks[oldItemPosition] == newDataset[newItemPosition]
            }
        })
        
        withContext(Dispatchers.Main) {
            
            topArtistTracks = newDataset
            diff.dispatchUpdatesTo(this@TopArtistTracksRecyclerViewAdapter)
        }
        
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopArtistTracksRecyclerViewAdapter.ViewHolder
    {
        return ViewHolder(TopArtistTracksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: TopArtistTracksRecyclerViewAdapter.ViewHolder, position: Int)
    {
        with(holder) {
            with(topArtistTracks[position]) {
                
                binding.trackNameTextView.text = this.name
                
                val df2 = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()))
                binding.playCountTextView.text = df2.format(this.playcount.toInt())
                binding.listenersCountTextView.text = df2.format(this.listeners.toInt())
            }
        }
    }
    
    override fun getItemCount(): Int
    {
        return topArtistTracks.size
    }
}