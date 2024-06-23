package com.example.final_project.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    private val weatherApiService = RetrofitClient.weatherApiService
    private val apiKey = BuildConfig.WEATHER_API_KEY
    val weatherData: MutableLiveData<Weather> = MutableLiveData()
    fun fetchWeather(currentDate: String,currentTime:String, nx:String, ny:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = weatherApiService.getCurrentWeather(currentDate,currentTime,nx,ny,apiKey).execute()
            if (response.isSuccessful){
                weatherData.postValue(response.body())
            }else{
                Log.e("viewmodel","not data")
            }
        }

    }
}