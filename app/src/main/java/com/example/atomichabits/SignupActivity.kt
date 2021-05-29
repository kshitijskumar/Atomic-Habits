package com.example.atomichabits

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.atomichabits.databinding.ActivitySignupBinding
import com.example.atomichabits.utils.Resource
import com.example.atomichabits.utils.UtilFunctions.showToast
import com.example.atomichabits.viewmodels.AuthViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private val viewModel by lazy {
        AuthViewModel.getAuthViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AtomicHabits)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        observeValues()
    }

    private fun setupViews() {
        binding.btnSignup.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etComfirmPassword.text.toString().trim()

            viewModel.signupNewUser(name, email, password, confirmPassword)
        }
    }

    private fun observeValues() {
        viewModel.signupState.observe(this) {
            when(it) {
                is Resource.Success -> {
                    stopLoading()
                    this.showToast("Successfully signed up! Please login to join the community")
                    finish()
                }
                is Resource.Error -> {
                    stopLoading()
                    this.showToast(it.errorMsg)
                }
                is Resource.Loading -> {
                    startLoading()
                }
            }
        }
    }

    private fun stopLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun startLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }
}