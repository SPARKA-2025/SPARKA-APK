package com.example.sparka_v0

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sparka_v0.ui.splashscreen.home.HomeFragment
import com.example.sparka_v0.ui.splashscreen.order.OrderFragment
import com.example.sparka_v0.ui.splashscreen.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val orderFragment = OrderFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.containerFragment, homeFragment).commit()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.containerFragment, homeFragment).commit()
                    true
                }
                R.id.nav_order -> {
                    supportFragmentManager.beginTransaction().replace(R.id.containerFragment, orderFragment).commit()
                    true
                }
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.containerFragment, profileFragment).commit()
                    true
                }
                else -> false
            }
        }
    }
}
