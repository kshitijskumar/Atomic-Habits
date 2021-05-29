package com.example.atomichabits

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.atomichabits.databinding.ActivityMainBinding
import com.example.atomichabits.utils.Injector

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupToolbarLogout()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.feedFragment -> {
                    setupToolBarTitle("Feed")
                    binding.bottomNavView.visibility = View.VISIBLE
                    binding.toolBar.navigationIcon = null
                }
                R.id.taskFragment -> {
                    setupToolBarTitle("Task")
                    binding.bottomNavView.visibility = View.VISIBLE
                    binding.toolBar.navigationIcon = null
                }
                R.id.profileFragment -> {
                    setupToolBarTitle("Profile")
                    binding.bottomNavView.visibility = View.VISIBLE
                    binding.toolBar.navigationIcon = null
                }
                R.id.uploadTaskFragment -> {
                    setupToolBarTitle("Upload your progress")
                    binding.bottomNavView.visibility = View.GONE
                    binding.toolBar.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_back)
                    binding.toolBar.setNavigationOnClickListener {
                        navController.navigateUp()
                    }
                }
            }
        }
    }

    private fun setupToolbarLogout() {
        sharedPreferences = applicationContext.getSharedPreferences("USER_STATUS", MODE_PRIVATE)
        binding.toolBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.logout -> {
                    sharedPreferences.edit()
                        .putString("token", null)
                        .putBoolean("isLoggedIn", false)
                        .putString("id", null)
                        .apply()

                    Injector.getInjector().removeTokenForUser()
                    Injector.getInjector().removeIdForUser()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupToolBarTitle(title: String) {
        binding.toolBar.title = title
    }
}