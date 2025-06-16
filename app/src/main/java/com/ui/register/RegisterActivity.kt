package com.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    //rev
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //rev
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //rev
        binding.btnLogin.setOnClickListener {
            val name = binding.NameEditText.text.toString().trim()
            val email = binding.EmailText.text.toString().trim()
            val password = binding.PasswordText.text.toString().trim()
            val confirm = binding.ConfirmText.text.toString().trim()

            //rev
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Semua formulir harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirm) { //rev
                Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, RegisterPlatActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
                putExtra(EXTRA_EMAIL, email)
                putExtra(EXTRA_PASSWORD, password)
            }
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_EMAIL = "email"
        const val EXTRA_PASSWORD = "password"
    }
}