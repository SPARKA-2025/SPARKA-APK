package com.example.bottomnavyt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.data.UserPreferences
import com.ui.login.Login
import kotlin.jvm.java

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 2000L // 2 detik
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        userPreferences = UserPreferences(this)

        //rev
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (userPreferences.isLoggedIn()) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, Login::class.java)
            }
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}