package com.example.sparka_v0.ui.splashscreen.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sparka_v0.R
import com.example.sparka_v0.MainActivity
import androidx.core.content.edit

class RegisterPlatActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_plat)

        val etPhone = findViewById<EditText>(R.id.PhoneEditText)
        val etPlat = findViewById<EditText>(R.id.PlatEditText)
        val etAlamat = findViewById<EditText>(R.id.AlamatEditText)
        val btnRegister = findViewById<Button>(R.id.btnLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val nama = intent.getStringExtra("nama") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        Log.d("RegisterDebug", "nama=$nama, email=$email, password=$password")

        btnRegister.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val platNomor = etPlat.text.toString().trim()

            if (phone.isBlank() || platNomor.isBlank() || alamat.isBlank()) {
                Toast.makeText(this, "Nomor HP, alamat dan plat wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (platNomor.length > 10) {
                Toast.makeText(this, "Plat nomor maksimal 10 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!phone.all { it.isDigit() }) {
                Toast.makeText(this, "Nomor HP harus berupa angka", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            viewModel.registerUser(nama, email, password, phone, alamat, platNomor) { success, result ->
                progressBar.visibility = View.GONE
                if (success) {
                    val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                    sharedPref.edit { putString("access_token", result) }

                    Toast.makeText(this, "Register sukses!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
