package com.example.final_project.home

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.final_project.R
import com.example.final_project.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dailyWeather = view.findViewById<WebView>(R.id.dailyWeather)
        val webView = view.findViewById<WebView>(R.id.petNews)
        webView.loadUrl("https://www.pet-news.or.kr/")
        dailyWeather.loadUrl("https://www.accuweather.com/ko/kr/siheung/223647/hourly-weather-forecast/223647")
    }
}