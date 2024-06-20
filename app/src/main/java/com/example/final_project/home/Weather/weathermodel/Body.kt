package com.example.final_project.home.Weather.weathermodel

data class Body(
    val dataType: String,
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)