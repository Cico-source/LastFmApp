package com.leon.lastfmapp.feature_lastfm.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import com.leon.lastfmapp.R
import com.leon.lastfmapp.common.util.snackbar
import com.leon.lastfmapp.databinding.FragmentTopArtistsScreenBinding
import com.leon.lastfmapp.feature_lastfm.presentation.viewmodels.TopArtistsScreenViewModel

@AndroidEntryPoint
class TopArtistsScreenFragment : Fragment(R.layout.fragment_top_artists_screen)
{
    
    private var _binding: FragmentTopArtistsScreenBinding? = null
    private val binding: FragmentTopArtistsScreenBinding
        get() = _binding!!
    
    private val viewModel: TopArtistsScreenViewModel by viewModels()
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTopArtistsScreenBinding.bind(view)
        
        subscribeToObservers()
        listenToEvents()
        
        //		binding.btnChangeCity.setOnClickListener {
        //
        //			findNavController().navigate(R.id.action_mainScreenFragment_to_searchScreenFragment)
        //		}
        
    }
    
    private fun listenToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        
        viewModel.setupEvent.collect { event ->
            
            when (event)
            {
                /*
				is TopArtistsScreenViewModel.SetupEvent.GetCityWeatherDetailsErrorEvent ->
				{
					binding.loadingSpinner.isVisible = false
					binding.btnRefresh.isVisible = true
					snackbar(event.error)
				}
				*/
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
                /*
				is TopArtistsScreenViewModel.SetupEvent.??? ->
				{
				
				}
				*/
                is TopArtistsScreenViewModel.SetupEvent.LoadingEvent ->
                {
                    // binding.loadingSpinner.isVisible = true
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

