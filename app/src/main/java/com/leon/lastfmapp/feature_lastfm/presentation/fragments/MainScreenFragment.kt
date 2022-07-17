package com.leon.lastfmapp.feature_lastfm.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.leon.lastfmapp.R
import com.leon.lastfmapp.databinding.FragmentMainScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen)
{
	
	private var _binding: FragmentMainScreenBinding? = null
	private val binding: FragmentMainScreenBinding
		get() = _binding!!
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentMainScreenBinding.bind(view)
		
		
		binding.btnTopTracks.setOnClickListener {

			findNavController().navigate(R.id.action_mainScreenFragment_to_topTracksScreenFragment)
		}
		
		binding.btnTopArtists.setOnClickListener {

			findNavController().navigate(R.id.action_mainScreenFragment_to_topArtistsScreenFragment)
		}
		
		binding.btnSearchArtists.setOnClickListener {

			findNavController().navigate(R.id.action_mainScreenFragment_to_searchScreenFragment,
				args = Bundle().apply { putString("backPressed", null)})
		}
	
	}
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
	
}