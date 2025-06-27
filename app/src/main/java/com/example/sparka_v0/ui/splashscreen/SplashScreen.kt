package com.example.sparka_v0.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sparka_v0.R
import com.example.sparka_v0.ui.splashscreen.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.jvm.java

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
            finish()
        }
    }
}
