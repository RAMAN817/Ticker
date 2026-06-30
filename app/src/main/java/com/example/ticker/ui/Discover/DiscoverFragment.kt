package com.example.ticker.ui.Discover

import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.example.ticker.viewmodel.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment: FragmentActivity() {
    private val ViewModel : DiscoverViewModel by viewModels()
    }
