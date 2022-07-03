package com.leon.lastfmapp.feature_lastfm.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.leon.lastfmapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashScreenFragment : Fragment()
{
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        lifecycleScope.launch {
            
            delay(1500)
            findNavController().navigate(R.id.action_splashScreenFragment_to_mainScreenFragment)
        }
    }
}