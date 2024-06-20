package com.example.basicbankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(kotlinx.coroutines.Runnable {
            run {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        },3000)
    }
}