package com.example.final_project.home

data class Weather (val response : RESPONSE)
data class RESPONSE(val header : HEADER, val body : BODY)
data class HEADER(val resultCode : String, val resultMsg : String)
data class BODY(val dataType : String, val items : ITEMS,val pageNo:Int, val numOfRows:Int, val totalCount : Int)
data class ITEMS(val item : List<ITEM>)
// category : 자료 구분 코드, fcstDate : 예측 날짜, fcstTime : 예측 시간, fcstValue : 예보 값
data class ITEM(val baseDate : String, val baseTime : String, val category : String, val nx:Int,val ny:Int,val obsrValue : String)