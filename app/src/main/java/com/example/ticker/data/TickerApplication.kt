package com.example.ticker.data

import android.app.Application
import android.util.Log

import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Executors

@HiltAndroidApp
class TickerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}