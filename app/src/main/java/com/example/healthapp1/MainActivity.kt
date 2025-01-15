package com.example.healthapp1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Setup the toolbar as the action bar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set the ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set the initial fragment (replace with your default fragment)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CourseListFragment()).commit()

        // Navigation item selected listener
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_appointments -> replaceFragment(HospitalListFragment())
                R.id.nav_pharmacy -> replaceFragment(PharmacyFragment())
                R.id.nav_profile -> replaceFragment(ProfileFragment())
                R.id.nav_settings -> replaceFragment(SettingsFragment())
                R.id.nav_support -> replaceFragment(SupportFragment())
                R.id.nav_logout -> logout() // Handle logout
                R.id.nav_Home -> home() // Navigate to home
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Emergency button click listener
        val emergencyButton = findViewById<ImageButton>(R.id.emergency_button)
        emergencyButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:108") // Replace with the actual ambulance number
            startActivity(intent)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        // Navigate back to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Finish MainActivity
    }

    private fun home() {
        // Go back to the home fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CourseListFragment()).commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
