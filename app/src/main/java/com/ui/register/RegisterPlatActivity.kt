package com.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ViewModelFactory
import com.data.Result
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.ActivityRegisterPlatBinding
import com.ui.login.Login

class RegisterPlatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPlatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterPlatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory.getInstance(this)
        val registerViewModel: RegisterViewModel by viewModels { viewModelFactory }

        val name = intent.getStringExtra("name") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val password = intent.getStringExtra("password") ?: ""

        binding.btnback.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            val platNomor = binding.PlatEditText.text.toString().trim()
            val jenisMobil = binding.JenisEditText.text.toString().trim()

            if (platNomor.isEmpty() || jenisMobil.isEmpty()) {
                Toast.makeText(this, "Semua formulir harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerViewModel.register(
                name = name,
                email = email,
                pass = password,
                jenisMobil = jenisMobil,
                plat = platNomor
            )
        }

        registerViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        // Observe Result
                registerViewModel.registResult.observe(this) { result ->
                    when (result) {
                        is Result.Loading -> Unit // handled by isLoading observer
                        is Result.Success -> {
                            Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Login::class.java))
                            finishAffinity()
                        }
                        is Result.Error -> {
                            Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }

        // Login text click
        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}