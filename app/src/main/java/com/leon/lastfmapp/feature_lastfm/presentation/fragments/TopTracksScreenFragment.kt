package com.leon.lastfmapp.feature_lastfm.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.leon.lastfmapp.R
import com.leon.lastfmapp.common.util.snackbar
import com.leon.lastfmapp.databinding.FragmentTopTracksScreenBinding
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.Track
import com.leon.lastfmapp.feature_lastfm.presentation.adapters.TopTracksRecyclerViewAdapter
import com.leon.lastfmapp.feature_lastfm.presentation.viewmodels.TopTracksScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopTracksScreenFragment : Fragment(R.layout.fragment_top_tracks_screen)
{
    
    private var _binding: FragmentTopTracksScreenBinding? = null
    private val binding: FragmentTopTracksScreenBinding
        get() = _binding!!
    
    private val viewModel: TopTracksScreenViewModel by viewModels()
    
    @Inject
    lateinit var topTracksAdapter: TopTracksRecyclerViewAdapter
    
    private var updateTopTracksJob: Job? = null
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTopTracksScreenBinding.bind(view)
        
        binding.topTracksRecyclerView.adapter = topTracksAdapter
        
        subscribeToObservers()
        listenToEvents()
        
        viewModel.getTopTracks()
    
        binding.btnRefresh.setOnClickListener {

            binding.loadingSpinner.isVisible = true
            binding.btnRefresh.isVisible = false
            viewModel.getTopTracks()
        }
        
    }
    
    private fun updateTopTracksRecyclerView(tracks: List<Track>)
    {
        updateTopTracksJob?.cancel()
        updateTopTracksJob = lifecycleScope.launch {
            
            topTracksAdapter.updateDataset(tracks)
        }
        
    }
    
    private fun listenToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        
        viewModel.setupEvent.collect { event ->
            
            when (event)
            {
				is TopTracksScreenViewModel.SetupEvent.GetTopTracksErrorEvent ->
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
				is TopTracksScreenViewModel.SetupEvent.GetTopTracksEvent ->
				{
                    event.topTracks.tracks.run {
                        
                        updateTopTracksRecyclerView(track)
                    }
                    
                    binding.loadingSpinner.isVisible = false
				}
                is TopTracksScreenViewModel.SetupEvent.LoadingEvent ->
                {
                    binding.loadingSpinner.isVisible = true
                }
                else                                                ->
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

