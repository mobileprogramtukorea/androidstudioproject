package com.example.final_project.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.BuildConfig
import com.example.final_project.home.Weather.WeatherService
import com.example.final_project.home.Weather.weathermodel.Weather
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeVIewModel : ViewModel() {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
    val weatherService = retrofit.create(WeatherService::class.java)

    fun getWeather() {
        Log.d("FUCK", "GO")
        viewModelScope.launch(Dispatchers.IO) {
            val weatherResult = runCatching {
                weatherService.getWeatherByName(
                    "10",
                    "1",
                    "20240620",
                    "0200",
                    "JSON",
                    "55",
                    "127"
                )
            }.onSuccess {
                Log.d("FUCK", "SUCCESS: $it")
                it.response.body.items.item.forEach {
                    Log.d("FUCK", "Category: ${it.category} - ${it.obsrValue}")
                }
            }.onFailure {
                Log.d("FUCK", "Failure: $it")
            }

            //Location
        }
    }
}