package com.example.weather4u.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.weather4u.R
import com.example.weather4u.databinding.ActivitySplashScreenBinding
import com.example.weather4u.util.SplashTimeOut


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)


        binding.mainTitle.startAnimation(topAnimation)
        binding.subTitle.startAnimation(bottomAnimation)
        binding.secounSubTitle.startAnimation(bottomAnimation)
        binding.mainImage.startAnimation(middleAnimation)

        val intent = Intent(this, WeatherActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, SplashTimeOut.toLong())
    }
}