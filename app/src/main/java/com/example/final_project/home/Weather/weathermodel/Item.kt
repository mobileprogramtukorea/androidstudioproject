package com.example.final_project.home.Weather.weathermodel

data class Item(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val obsrValue: String
)