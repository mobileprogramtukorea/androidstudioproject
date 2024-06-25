package com.example.final_project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.final_project.databinding.WalkingWithDogBinding
import com.google.android.gms.location.*
import kotlin.math.round

class WalkActivity : AppCompatActivity() {

    private lateinit var binding: WalkingWithDogBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var lastLocation: Location? = null
    private var totalDistance = 0.0
    private var isWalking = false
    private var isPaused = false
    private var startTime = 0L
    private var pauseTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (isWalking && !isPaused) {
                    for (location in locationResult.locations) {
                        binding.distanceTextView.text = "0 m"
                        if (lastLocation != null) {
                            val distance = lastLocation!!.distanceTo(location).toDouble()
                            totalDistance += distance
                            binding.distanceTextView.text = "${totalDistance} m"
                            binding.caloris.text = "${round(totalDistance*0.063)} Kcal"
                        }
                        lastLocation = location
                    }
                }
            }
        }
        binding = WalkingWithDogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        startWalking()

        binding.pauseButton.setOnClickListener {
            pauseWalking()
            binding.pauseButton.visibility = View.GONE
            binding.resumeButton.visibility = View.VISIBLE
            binding.stopButton.visibility = View.VISIBLE
        }
        //text
        binding.resumeButton.setOnClickListener {
            resumeWalking()
            binding.pauseButton.visibility = View.VISIBLE
            binding.resumeButton.visibility = View.GONE
            binding.stopButton.visibility = View.GONE
        }
        binding.stopButton.setOnClickListener {
            val resultData = totalDistance*totalDistance*0.063/binding.chronometer.base
            val resultIntent = Intent()
            resultIntent.putExtra("resultData", resultData)
            setResult(RESULT_OK,resultIntent)
            stopWalking()
        }
        binding.buttonCall112.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:112")
            startActivity(callIntent)
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun startWalking() {
        if (!isWalking) {
            isWalking = true
            totalDistance = 0.0
            lastLocation = null
            startTime = SystemClock.elapsedRealtime()
            binding.chronometer.base = startTime
            binding.chronometer.start()
            startLocationUpdates()
        }
    }

    private fun pauseWalking() {
        if (isWalking && !isPaused) {
            pauseTime = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            isPaused = true
            stopLocationUpdates()
        }
    }

    private fun resumeWalking() {
        if (isWalking && isPaused) {
            val elapsedPauseTime = SystemClock.elapsedRealtime() - pauseTime
            binding.chronometer.base += elapsedPauseTime
            binding.chronometer.start()
            isPaused = false
            startLocationUpdates()
        }
    }

    private fun stopWalking() {
        if (isWalking) {
            binding.chronometer.stop()
            isWalking = false
            isPaused = false
            stopLocationUpdates()
            Toast.makeText(this, "최종거리: ${totalDistance.toInt()} m", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 5000
        ).apply {
            setMinUpdateIntervalMillis(2000)
            setMaxUpdateDelayMillis(10000)
        }.build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}