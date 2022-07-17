package com.leon.lastfmapp.feature_lastfm.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.leon.lastfmapp.R
import com.leon.lastfmapp.common.util.snackbar
import com.leon.lastfmapp.databinding.FragmentTopArtistsScreenBinding
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.Artist
import com.leon.lastfmapp.feature_lastfm.presentation.adapters.TopArtistsRecyclerViewAdapter
import com.leon.lastfmapp.feature_lastfm.presentation.viewmodels.TopArtistsScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopArtistsScreenFragment : Fragment(R.layout.fragment_top_artists_screen)
{
    
    private var _binding: FragmentTopArtistsScreenBinding? = null
    private val binding: FragmentTopArtistsScreenBinding
        get() = _binding!!
    
    private val viewModel: TopArtistsScreenViewModel by viewModels()
    
    @Inject
    lateinit var topArtistsAdapter: TopArtistsRecyclerViewAdapter
    
    private var updateTopArtistsJob: Job? = null
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTopArtistsScreenBinding.bind(view)
    
        binding.topArtistsRecyclerView.adapter = topArtistsAdapter
        
        subscribeToObservers()
        listenToEvents()
        
        viewModel.getTopArtists()
    
        binding.btnRefresh.setOnClickListener {
        
            binding.loadingSpinner.isVisible = true
            binding.btnRefresh.isVisible = false
            viewModel.getTopArtists()
        }
    
        topArtistsAdapter.setOnItemClickListener { artistName: String ->
    
            findNavController().navigate(R.id.action_topArtistsScreenFragment_to_artistDetailScreenFragment,
                args = Bundle().apply {
                    putString("artistName", artistName )
                    putString("screen", "TopArtistsScreen")
                }
                )
        }
        
    }
    
    private fun updateTopArtistsRecyclerView(artists: List<Artist>)
    {
        updateTopArtistsJob?.cancel()
        updateTopArtistsJob = lifecycleScope.launch {
            
            topArtistsAdapter.updateDataset(artists)
        }
        
    }
    
    private fun listenToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        
        viewModel.setupEvent.collect { event ->
            
            when (event)
            {
				is TopArtistsScreenViewModel.SetupEvent.GetTopArtistsErrorEvent ->
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
				is TopArtistsScreenViewModel.SetupEvent.GetTopArtistsEvent ->
				{
                    
                    event.topArtists.artists.run {
                        
                        updateTopArtistsRecyclerView(artist)
                    }
                    
                    binding.loadingSpinner.isVisible = false
				}
                is TopArtistsScreenViewModel.SetupEvent.LoadingEvent ->
                {
                    binding.loadingSpinner.isVisible = true
                }
                else                                                 ->
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

