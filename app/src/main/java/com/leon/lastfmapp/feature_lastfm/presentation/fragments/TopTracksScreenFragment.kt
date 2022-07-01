package com.leon.lastfmapp.feature_lastfm.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import com.leon.lastfmapp.R
import com.leon.lastfmapp.common.util.snackbar
import com.leon.lastfmapp.databinding.FragmentTopTracksScreenBinding
import com.leon.lastfmapp.feature_lastfm.presentation.viewmodels.TopTracksScreenViewModel

@AndroidEntryPoint
class TopTracksScreenFragment : Fragment(R.layout.fragment_top_tracks_screen)
{
    
    private var _binding: FragmentTopTracksScreenBinding? = null
    private val binding: FragmentTopTracksScreenBinding
        get() = _binding!!
    
    private val viewModel: TopTracksScreenViewModel by viewModels()
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTopTracksScreenBinding.bind(view)
        
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
				is TopTracksScreenViewModel.SetupEvent.GetCityWeatherDetailsErrorEvent ->
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
				is TopTracksScreenViewModel.SetupEvent.??? ->
				{
				
				}
				*/
                is TopTracksScreenViewModel.SetupEvent.LoadingEvent ->
                {
                    // binding.loadingSpinner.isVisible = true
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
