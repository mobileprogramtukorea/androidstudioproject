package com.example.final_project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.final_project.databinding.WalkingWithDogBinding
import com.google.android.gms.location.*
import kotlin.math.round

class WalkActivity : AppCompatActivity() {

    private lateinit var distanceTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var stopButton: ImageButton
    private lateinit var restartButton: ImageButton
    private lateinit var finishButton: ImageButton
    private lateinit var stopButtonText: TextView
    private lateinit var restartButtonText: TextView
    private lateinit var finishButtonText: TextView
    private lateinit var animatedImageView: ImageView
    private lateinit var binding: WalkingWithDogBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private var isWalking = false
    private var startTime: Long = 0
    private var totalDistance = 0.0
    private var totalElapsedTime: Long = 0
    private var isPaused = false
    private var lastLocation: Location? = null

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
        private val images = intArrayOf(R.drawable.walk2, R.drawable.walk3)

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
        stopButton = findViewById(R.id.stopButton)
        restartButton = findViewById(R.id.restartButton)
        finishButton = findViewById(R.id.finishButton)
        stopButtonText = findViewById(R.id.stopButtonText)
        restartButtonText = findViewById(R.id.restartButtonText)
        finishButtonText = findViewById(R.id.finishButtonText)
        animatedImageView = findViewById(R.id.animatedImageView)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (isWalking && !isPaused) {
                    for (location in locationResult.locations) {
                        binding.distanceTextView.text = "0 m"
                        if (lastLocation != null) {
                            val distance = lastLocation!!.distanceTo(location).toDouble()
                            totalDistance += distance
                            binding.distanceTextView.text = "${totalDistance} m"
                            binding.calorie.text = "${round(totalDistance*0.063)} Kcal"
                        }
                        lastLocation = location
                    }
                }
            }
        }

        stopButton.setOnClickListener {
            pauseWalking()
            stopButton.visibility = View.GONE
            stopButtonText.visibility = View.GONE
            restartButton.visibility = View.VISIBLE
            restartButtonText.visibility = View.VISIBLE
            finishButton.visibility = View.VISIBLE
            finishButtonText.visibility = View.VISIBLE
            handler.post(imagePauseSwitcherRunnable)
        }

        restartButton.setOnClickListener {
            resumeWalking()
            restartButton.visibility = View.GONE
            restartButtonText.visibility = View.GONE
            finishButton.visibility = View.GONE
            finishButtonText.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
            stopButtonText.visibility = View.VISIBLE
            handler.post(imageSwitcherRunnable)
        }

        finishButton.setOnClickListener {
            stopWalking()
            restartButton.visibility = View.GONE
            restartButtonText.visibility = View.GONE
            finishButton.visibility = View.GONE
            finishButtonText.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
            stopButtonText.visibility = View.VISIBLE
        }

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
        Toast.makeText(this, "최종거리: ${totalDistance.toInt()} m", Toast.LENGTH_LONG).show()
        finish()
    }

    fun callPolice(view: View) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:112")
        startActivity(intent)
    }
}