package com.example.final_project.home

import com.example.final_project.home.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("getUltraSrtNcst")
    fun getCurrentWeather(
        @Query("base_date") base_date : String,  // 발표 일자
        @Query("base_time") base_time : String,  // 발표 시각
        @Query("nx") nx : String,                // 예보지점 X 좌표
        @Query("ny") ny : String,
        @Query("serviceKey")apiKey:String,
        @Query("numOfRows") num_of_rows : Int = 10,   // 한 페이지 경과 수
        @Query("pageNo") page_no : Int = 1,          // 페이지 번호
        @Query("dataType") data_type : String = "Json"  // 응답 자료 형식
    )
    : Call<Weather>
}