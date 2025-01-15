package com.example.healthapp1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize ImageView and TextView
        val logoImageView: ImageView = findViewById(R.id.logoImageView)
        val titleTextView: TextView = findViewById(R.id.titleTextView)

        // Load animations
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        // Start animations
        logoImageView.startAnimation(fadeInAnimation)
        titleTextView.startAnimation(slideUpAnimation)

        // Delay for 2 seconds, then navigate to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish() // Close SplashActivity
        }, 2000) // Adjust the delay as per your preference
    }
}
