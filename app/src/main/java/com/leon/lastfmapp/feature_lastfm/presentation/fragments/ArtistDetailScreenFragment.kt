package com.leon.lastfmapp.feature_lastfm.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.leon.lastfmapp.R
import com.leon.lastfmapp.common.util.snackbar
import com.leon.lastfmapp.databinding.FragmentArtistDetailScreenBinding
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.Track
import com.leon.lastfmapp.feature_lastfm.presentation.adapters.TopArtistTracksRecyclerViewAdapter
import com.leon.lastfmapp.feature_lastfm.presentation.viewmodels.ArtistDetailScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ArtistDetailScreenFragment : Fragment(R.layout.fragment_artist_detail_screen)
{
    
    private var _binding: FragmentArtistDetailScreenBinding? = null
    private val binding: FragmentArtistDetailScreenBinding
        get() = _binding!!
    
    private val viewModel: ArtistDetailScreenViewModel by viewModels()
    
    private val args: ArtistDetailScreenFragmentArgs by navArgs()
    
    @Inject
    lateinit var topArtistTracksAdapter: TopArtistTracksRecyclerViewAdapter
    
    private var updateTopArtistTracksJob: Job? = null
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArtistDetailScreenBinding.bind(view)
    
        binding.topArtistTracksRecyclerView.adapter = topArtistTracksAdapter
        
        subscribeToObservers()
        listenToEvents()
        
        viewModel.getArtistDetail(args.artistName)
    
        binding.btnRefresh.setOnClickListener {
        
            binding.loadingSpinner.isVisible = true
            binding.btnRefresh.isVisible = false
            viewModel.getArtistDetail(args.artistName)
        }
        
    }
    
    private fun updateTopArtistsRecyclerView(tracks: List<Track>)
    {
        updateTopArtistTracksJob?.cancel()
        updateTopArtistTracksJob = lifecycleScope.launch {
            
            topArtistTracksAdapter.updateDataset(tracks)
        }
        
    }
    
    private fun listenToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        
        viewModel.setupEvent.collect { event ->
            
            when (event)
            {
				is ArtistDetailScreenViewModel.SetupEvent.GetArtistDetailErrorEvent ->
				{
                    binding.loadingSpinner.isVisible = false
					binding.btnRefresh.isVisible = true
					snackbar(event.error)
				}
                else ->
                {
                    Unit
                }
            }
            
        }
        
    }
    
    private fun subscribeToObservers() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        
        viewModel.screen.collect { event ->
            
            when (event)
            {
				is ArtistDetailScreenViewModel.SetupEvent.GetArtistDetailEvent ->
				{
				    binding.wrapperConstraintLayout.isVisible = true
                    
                    event.artistDetail.first.artist.run {
                    
                        Glide.with(binding.albumIconImageView.context)
                            .load(this.image[1].text)
                            .into(binding.albumIconImageView)
                        
                        binding.artistNameTextView.text = this.name
                        
                        binding.tag1TextView.text = this.tags.tag.getOrNull(0)?.name ?: ""
                        binding.tag2TextView.text = this.tags.tag.getOrNull(1)?.name ?: ""
                        binding.tag3TextView.text = this.tags.tag.getOrNull(2)?.name ?: ""
                        binding.tag4TextView.text = this.tags.tag.getOrNull(3)?.name ?: ""
                        binding.tag5TextView.text = this.tags.tag.getOrNull(4)?.name ?: ""
                        
                        binding.summaryTextView.text = this.bio.summary
                        
                        /*
                        binding.summaryTextView.settings.defaultFontSize = 17
                        
                        binding.summaryTextView.loadData(
                            "<html><head><style> p {line-height: 1.5;}</style></head><body> <p>" + this.bio.summary + "</p></body></html>", "text/html", "utf-8"

                        )
                        */
    
                        binding.playCountTextView.text = this.stats.playcount
                        binding.listenersCountTextView.text = this.stats.listeners
                        
                    }
                    
                    event.artistDetail.second.toptracks.run {
                        
                        updateTopArtistsRecyclerView(track)
                    }
                    
                    binding.loadingSpinner.isVisible = false
                    
				}
                is ArtistDetailScreenViewModel.SetupEvent.LoadingEvent ->
                {
                    binding.loadingSpinner.isVisible = true
                }
                else                                                   ->
                {
                    Unit
                }
            }
            
        }
        
    }
    
    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}

