package com.example.bottomnavyt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.data.UserPreferences
import com.example.bottomnavyt.databinding.ActivityMainBinding
import com.ui.home.Home
import com.ui.listbooked.Order
import com.ui.login.Login

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        userPreferences = UserPreferences(this)

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(Home())
                R.id.profile -> replaceFragment(Order())
//                R.id.settings -> replaceFragment(Settings())

                else ->{

                }

            }
            true
        }


    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    private fun checkTokenExpiry() {
        if (userPreferences.isTokenExpired()) {
            showSessionExpiredPopup()
        }
    }

    private fun showSessionExpiredPopup() {
        AlertDialog.Builder(this)
            .setTitle("Session Expired")
            .setMessage("Your session has expired. Please log in again.")
            .setPositiveButton("OK") { _, _ ->
                // Redirect to login screen
                Toast.makeText(this, "Sesi Anda telah berakhir", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()

            }
            .show()
    }
}