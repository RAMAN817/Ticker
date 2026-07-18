package com.example.ticker.data

import android.app.Application
import android.util.Log
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewOutcomeReceiver
import androidx.webkit.WebViewStartUpConfig
import androidx.webkit.WebViewStartUpResult
import androidx.webkit.WebViewStartupException
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Executors

@HiltAndroidApp
class TickerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val config = WebViewStartUpConfig.Builder(Executors.newSingleThreadExecutor()).build()

        WebViewCompat.startUpWebView(
            this,
            config,
            object : WebViewOutcomeReceiver<WebViewStartUpResult, WebViewStartupException> {
                override fun onResult(result: WebViewStartUpResult) {
                    // startup complete, no-op needed
                }

                override fun onError(error: WebViewStartupException) {
                    Log.w("TickerApplication", "WebView pre-warm failed", error)
                }
            }
        )
    }
}