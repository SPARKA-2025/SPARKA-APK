package com.example.sparka_v0.ui.splashscreen.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sparka_v0.R
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.sparka_v0.MainActivity
import com.google.android.material.textfield.TextInputEditText
import androidx.core.content.edit
import com.example.sparka_v0.ui.splashscreen.register.RegisterActivity


class LoginActivity : AppCompatActivity() {

    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtRegister = findViewById<TextView>(R.id.txtRegister)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password wajib diisi", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            viewModel.login(email, password) { success, result ->
                progressBar.visibility = View.GONE

                if (success) {
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                    Log.d("TOKEN", result)
                    val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                    sharedPref.edit { putString("access_token", result) }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                    Log.e("LoginError", result)
                }
            }
        }
    }
}