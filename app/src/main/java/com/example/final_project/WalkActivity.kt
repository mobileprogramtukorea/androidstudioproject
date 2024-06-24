package com.example.final_project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WalkActivity : AppCompatActivity() {

    private lateinit var distanceTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var pauseButton: ImageButton
    private lateinit var resumeButton: ImageButton
    private lateinit var stopButton: ImageButton
    private lateinit var pauseButtonText: TextView
    private lateinit var stopButtonText: TextView
    private lateinit var resumeButtonText: TextView
    private lateinit var animatedImageView: ImageView

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

    private val imageSwitcherRunnable = object : Runnable {
        private var imageIndex = 0
        private val images = intArrayOf(R.drawable.walking1, R.drawable.walking2)

        override fun run() {
            if (isWalking) {
                animatedImageView.setImageResource(images[imageIndex])
                imageIndex = (imageIndex + 1) % images.size
                handler.postDelayed(this, 500)
            }
        }
    }

    private val imagePauseSwitcherRunnable = object : Runnable {
        private var imageIndex = 0
        private val images = intArrayOf(R.drawable.walking1, R.drawable.walking2)

        override fun run() {
            if (!isWalking) {
                animatedImageView.setImageResource(images[imageIndex])
                imageIndex = (imageIndex + 1) % images.size
                handler.postDelayed(this, 500)
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
        pauseButtonText = findViewById(R.id.pauseButtonText)
        resumeButtonText = findViewById(R.id.resumeButtonText)
        stopButtonText = findViewById(R.id.stopButtonText)
        animatedImageView = findViewById(R.id.animatedImageView)

        pauseButton.setOnClickListener {
            pauseWalking()
            pauseButton.visibility = View.GONE
            pauseButtonText.visibility = View.GONE
            resumeButton.visibility = View.VISIBLE
            resumeButtonText.visibility = View.VISIBLE
            stopButton.visibility = View.VISIBLE
            stopButtonText.visibility = View.VISIBLE
            handler.post(imagePauseSwitcherRunnable)
        }

        resumeButton.setOnClickListener {
            resumeWalking()
            resumeButton.visibility = View.GONE
            resumeButtonText.visibility = View.GONE
            stopButton.visibility = View.GONE
            stopButtonText.visibility = View.GONE
            pauseButton.visibility = View.VISIBLE
            pauseButtonText.visibility = View.VISIBLE
            handler.post(imageSwitcherRunnable)
        }

        stopButton.setOnClickListener {
            stopWalking()
        }

        findViewById<View>(R.id.pauseLayout).visibility = View.GONE
        startWalking()
    }

    private fun startWalking() {
        isWalking = true
        startTime = System.currentTimeMillis()
        handler.post(updateRunnable)
        handler.post(imageSwitcherRunnable)
    }

    private fun pauseWalking() {
        isWalking = false
        totalElapsedTime += (System.currentTimeMillis() - startTime) / 1000
        handler.removeCallbacks(imageSwitcherRunnable)
    }

    private fun resumeWalking() {
        isWalking = true
        startTime = System.currentTimeMillis() - totalElapsedTime * 1000
        handler.post(updateRunnable)
        handler.post(imageSwitcherRunnable)
    }

    private fun stopWalking() {
        isWalking = false
        handler.removeCallbacks(updateRunnable)
        handler.removeCallbacks(imageSwitcherRunnable)
        handler.removeCallbacks(imagePauseSwitcherRunnable)
        finish()
    }

    fun callPolice(view: View) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:112")
        startActivity(intent)
    }
}
