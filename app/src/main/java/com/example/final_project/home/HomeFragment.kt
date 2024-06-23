package com.example.final_project.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.final_project.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment: Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val cal = Calendar.getInstance()
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 시각
        val timeM = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time) // 현재 분
        var tempText =""
        val base_time = getBaseTime(timeH, timeM)
        var base_date = crrDate(timeH, base_time)

        val temperature = view.findViewById<TextView>(R.id.temperature)
        val weatherDescription = view.findViewById<TextView>(R.id.weatherDescription)
        val humidity = view.findViewById<TextView>(R.id.humidity)
        //날씨정보 띄워주기
        viewModel.weatherData.observe(viewLifecycleOwner, Observer{
            when(it.response.body.items.item[0].obsrValue){
                "0" -> weatherDescription.text = "비 예보 없음"
                "1" -> weatherDescription.text = "비"
                "2" -> weatherDescription.text = "진눈깨비"
                "3" -> weatherDescription.text = "눈"
                "4" -> weatherDescription.text = "소나기"
            }
            tempText = "습도 : "+it.response.body.items.item[1].obsrValue + "%"
            humidity.text = tempText
            tempText = "온도 : "+it.response.body.items.item[3].obsrValue
            temperature.text = tempText
        })
        viewModel.fetchWeather(base_date,base_time,"55","127")
        val walkingButton = view.findViewById<Button>(R.id.startWalking)
        val dailyWeather = view.findViewById<WebView>(R.id.dailyWeather)

        val webView = view.findViewById<WebView>(R.id.petNews)
        webView.loadUrl("https://www.pet-news.or.kr/")
        dailyWeather.loadUrl("https://www.accuweather.com/ko/kr/siheung/223647/hourly-weather-forecast/223647")
    }
    private fun crrDate(t: String, bt: String): String{
        val cal = Calendar.getInstance()
        if (t == "00" && bt == "2330") {
            cal.add(Calendar.DATE, -1).toString()
            return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }else{
            return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }
    }
    private fun getBaseTime(h : String, m : String) : String {
        var result = ""

        // 45분 전이면
        if (m.toInt() < 45) {
            // 0시면 2330
            if (h == "00") result = "2330"
            // 아니면 1시간 전 날씨 정보 부르기
            else {
                var resultH = h.toInt() - 1
                // 1자리면 0 붙여서 2자리로 만들기
                if (resultH < 10) result = "0" + resultH + "30"
                // 2자리면 그대로
                else result = resultH.toString() + "30"
            }
        }
        // 45분 이후면 바로 정보 받아오기
        else result = h + "30"

        return result
    }

}