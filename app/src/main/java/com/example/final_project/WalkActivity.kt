package com.example.final_project


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class WalkActivity : AppCompatActivity() {

    private lateinit var distanceTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var pauseButton: Button
    private lateinit var resumeButton: Button
    private lateinit var stopButton: Button

    private var isWalking = false
    private var startTime: Long = 0
    private var totalDistance = 0.0f
    private var totalElapsedTime: Long = 0

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            if (isWalking) {
                val elapsedTime = (System.currentTimeMillis() - startTime) / 1000
                timeTextView.text = elapsedTime.toString()
                // 여기서 실제 거리 측정 로직을 추가하세요.
                distanceTextView.text = totalDistance.toString()
                handler.postDelayed(this, 1000)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.walking_with_dog)

        distanceTextView = findViewById(R.id.distanceTextView)
        timeTextView = findViewById(R.id.timeTextView)
        pauseButton = findViewById(R.id.pauseButton)
        resumeButton = findViewById(R.id.resumeButton)
        stopButton = findViewById(R.id.stopButton)

        pauseButton.setOnClickListener {
            pauseWalking()
            pauseButton.visibility = View.GONE
            resumeButton.visibility = View.VISIBLE
            stopButton.visibility = View.VISIBLE
        }

        resumeButton.setOnClickListener {
            resumeWalking()
            resumeButton.visibility = View.GONE
            stopButton.visibility = View.GONE
            pauseButton.visibility = View.VISIBLE
        }

        stopButton.setOnClickListener {
            stopWalking()
        }
        startWalking()

    }
    private fun startWalking() {
        isWalking = true
        startTime = System.currentTimeMillis()
        handler.post(updateRunnable)
    }

    private fun pauseWalking() {
        isWalking = false
        totalElapsedTime += (System.currentTimeMillis() - startTime) / 1000
    }

    private fun resumeWalking() {
        isWalking = true
        startTime = System.currentTimeMillis() - totalElapsedTime * 1000
        handler.post(updateRunnable)
    }

    private fun stopWalking() {
        isWalking = false
        handler.removeCallbacks(updateRunnable)
        // 추가로 산책 종료 처리 로직을 여기에 작성하세요.
        finish()
    }

}