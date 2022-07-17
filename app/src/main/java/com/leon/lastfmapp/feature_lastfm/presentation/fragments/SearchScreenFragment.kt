package com.leon.lastfmapp.feature_lastfm.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.leon.lastfmapp.R
import com.leon.lastfmapp.common.util.snackbar
import com.leon.lastfmapp.databinding.FragmentSearchScreenBinding
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.Artist
import com.leon.lastfmapp.feature_lastfm.presentation.adapters.SearchedArtistsRecyclerViewAdapter
import com.leon.lastfmapp.feature_lastfm.presentation.viewmodels.SearchScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.fragment_search_screen)
{
    
    private var _binding: FragmentSearchScreenBinding? = null
    private val binding: FragmentSearchScreenBinding
        get() = _binding!!
    
    private val viewModel: SearchScreenViewModel by viewModels()
    
    private val args: SearchScreenFragmentArgs by navArgs()
    
    @Inject
    lateinit var searchedArtistsAdapter: SearchedArtistsRecyclerViewAdapter
    
    private var updateSearchedArtistsJob: Job? = null
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchScreenBinding.bind(view)
    
        binding.topArtistsRecyclerView.adapter = searchedArtistsAdapter
        
        subscribeToObservers()
        listenToEvents()
        
        binding.searchArtistsEditText.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
            
            }
    
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
            
            }
    
            override fun afterTextChanged(s: Editable?)
            {
                viewModel.getArtists(s.toString())
            }
        })
    
        if (args.backPressed != null)
        {
            binding.searchArtistsEditText.setText(args.backPressed)
        }
    
        binding.btnRefresh.setOnClickListener {
        
            binding.loadingSpinner.isVisible = true
            binding.btnRefresh.isVisible = false
            viewModel.getArtists(binding.searchArtistsEditText.text.toString())
        }
    
        searchedArtistsAdapter.setOnItemClickListener { artistName: String ->
        
            findNavController().navigate(R.id.action_searchScreenFragment_to_artistDetailScreenFragment,
                args = Bundle().apply {
                    putString("artistName", artistName)
                    putString("screen", "SearchScreen")
                }
            )
        }
        
    }
    
    private fun updateSearchedArtistsRecyclerView(artists: List<Artist>)
    {
        updateSearchedArtistsJob?.cancel()
        updateSearchedArtistsJob = lifecycleScope.launch {
            
            searchedArtistsAdapter.updateDataset(artists)
        }
        
    }
    
    private fun listenToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        
        viewModel.setupEvent.collect { event ->
            
            when (event)
            {
				is SearchScreenViewModel.SetupEvent.GetSearchArtistsErrorEvent ->
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
				is SearchScreenViewModel.SetupEvent.GetSearchArtistsEvent ->
				{
                    event.searchedArtists.results.run {
                        
                        updateSearchedArtistsRecyclerView(artistmatches.artist)
                    }
                    
                    binding.loadingSpinner.isVisible = false
                    binding.btnRefresh.isVisible = false
				}
                is SearchScreenViewModel.SetupEvent.LoadingEvent ->
                {
                    binding.loadingSpinner.isVisible = true
                }
                is SearchScreenViewModel.SetupEvent.EmptyEvent ->
                {
                    binding.loadingSpinner.isVisible = false
                }
                else                                             ->
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

