package com.example.ticker.ui.discover

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.ticker.viewmodel.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment: Fragment() {
    private val ViewModel : DiscoverViewModel by viewModels()
    }
