package com.example.final_project.home

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val weatherApiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}