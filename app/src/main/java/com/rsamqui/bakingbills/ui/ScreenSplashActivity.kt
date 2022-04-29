package com.rsamqui.bakingbills.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rsamqui.bakingbills.navigation.MainActivity
import com.rsamqui.bakingbills.R
import com.rsamqui.bakingbills.databinding.ActivityScreenSplashBinding

class ScreenSplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScreenSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startSplashScreen()

    }

    private fun startSplashScreen(timeDelayInMilis : Long = 5000) {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                startFirstActivity()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }, timeDelayInMilis
        )
    }

    private fun startFirstActivity() = startActivity(Intent(this, MainActivity::class.java))


}