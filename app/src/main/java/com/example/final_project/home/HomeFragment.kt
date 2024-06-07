package com.example.final_project.home

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.final_project.R

class HomeFragment: Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val walkingButton = view.findViewById<Button>(R.id.startWalking)
        val dailyWeather = view.findViewById<WebView>(R.id.dailyWeather)
        val webView = view.findViewById<WebView>(R.id.petNews)
        webView.loadUrl("https://www.pet-news.or.kr/")
        dailyWeather.loadUrl("https://www.accuweather.com/ko/kr/siheung/223647/hourly-weather-forecast/223647")
        walkingButton.setOnClickListener {
            //산책화면으로 넘어가기

        }
    }
}