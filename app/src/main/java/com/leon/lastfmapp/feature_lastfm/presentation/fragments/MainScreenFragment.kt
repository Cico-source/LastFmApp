package com.leon.lastfmapp.feature_lastfm.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.leon.lastfmapp.R
import com.leon.lastfmapp.common.util.snackbar
import com.leon.lastfmapp.databinding.FragmentMainScreenBinding
import com.leon.lastfmapp.feature_lastfm.presentation.viewmodels.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen)
{
	
	private var _binding: FragmentMainScreenBinding? = null
	private val binding: FragmentMainScreenBinding
		get() = _binding!!
	
	private val viewModel: MainScreenViewModel by viewModels()
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentMainScreenBinding.bind(view)

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
				is MainScreenViewModel.SetupEvent.GetCityWeatherDetailsErrorEvent ->
				{
					binding.loadingSpinner.isVisible = false
					binding.btnRefresh.isVisible = true
					snackbar(event.error)
				}
				*/
				else                                                              ->
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
				is MainScreenViewModel.SetupEvent.??? ->
				{
				
				}
				*/
				is MainScreenViewModel.SetupEvent.LoadingEvent     ->
				{
					// binding.loadingSpinner.isVisible = true
				}
				else                                                     ->
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