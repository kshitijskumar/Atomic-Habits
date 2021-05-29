package com.example.atomichabits

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.atomichabits.databinding.ActivityLoginBinding
import com.example.atomichabits.utils.Injector
import com.example.atomichabits.utils.Resource
import com.example.atomichabits.utils.UtilFunctions.showToast
import com.example.atomichabits.viewmodels.AuthViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupPreferences()
        setupViews()
        observeValues()
    }

    override fun onStart() {
        super.onStart()

        if(isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupPreferences() {
        sharedPreferences = applicationContext.getSharedPreferences("USER_STATUS", MODE_PRIVATE)
    }

    private fun setupViewModel() {
        viewModel = AuthViewModel.getAuthViewModel(this)
    }

    private fun setupViews() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            viewModel.loginExistingUser(email, password)
        }

        binding.gotoSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeValues() {
        viewModel.loginState.observe(this) {
            when(it) {
                is Resource.Error -> {
                    stopLoading()
                    this.showToast(it.errorMsg)
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    stopLoading()
                    storeTokenInPreferences(it.data.token!!, it.data._id!!)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun storeTokenInPreferences(token: String, id: String) {
        sharedPreferences.edit()
            .putString("token", token)
            .putBoolean("isLoggedIn", true)
            .putString("id", id)
            .apply()
        Injector.getInjector().storeTokenForUser(token)
        Injector.getInjector().storeIdForUser(id)
    }

    private fun isLoggedIn() : Boolean {
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val isValidToken = sharedPreferences.getString("token", null)
        val isValidId = sharedPreferences.getString("id", null)

        isValidToken?.let {
            Injector.getInjector().storeTokenForUser(it)
        }
        isValidId?.let {
            Injector.getInjector().storeIdForUser(it)
        }
        return isLoggedIn && (isValidToken != null) && (isValidId != null)
    }
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.progressBar.visibility = View.GONE
    }


}