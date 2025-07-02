package com.example.sparka_v0.ui.splashscreen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sparka_v0.R
import com.example.sparka_v0.ui.splashscreen.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.sparka_v0.MainActivity


class SplashScreen : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE)

        lifecycleScope.launch {
            delay(2000)

            val token = sharedPreferences.getString("access_token", null)

            if (!token.isNullOrEmpty()) {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
            }

            finish()
        }
    }
}