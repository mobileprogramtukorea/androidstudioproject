package com.example.final_project.home.Weather
import com.example.final_project.BuildConfig
import com.example.final_project.home.Weather.weathermodel.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("getUltraSrtNcst?")
    suspend fun getWeatherByName(
        @Query("numOfRows") num_of_rows: String,
        @Query("pageNo") page_no: String,
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("dataType") datatype: String,
        @Query("nx") nx: String,
        @Query("ny") ny: String,
        @Query("serviceKey")serviceKey: String = BuildConfig.WEATHER_API_KEY
    ): Weather
}
