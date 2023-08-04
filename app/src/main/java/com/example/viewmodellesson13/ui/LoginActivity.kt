package com.example.viewmodellesson13.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson13.data.model.UserLogin
import com.example.viewmodellesson13.data.state.LoginState
import com.example.viewmodellesson13.databinding.ActivityLoginBinding
import com.example.viewmodellesson13.viewmodel.LoginViewModel
import com.example.viewmodellesson13.viewmodel.ProductsActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()

        listeners()
    }

    private fun observers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect {
                    when (it) {
                        is LoginState.Idle -> Unit
                        is LoginState.Loading -> {
                            binding.btnLogin.isVisible = false
                            binding.progressBar.isVisible = true
                        }

                        is LoginState.Success -> {
                            binding.btnLogin.isVisible = true
                            binding.progressBar.isVisible = false
                            startActivity(Intent(this@LoginActivity, ProductsActivity::class.java))
                        }

                        is LoginState.Error -> {
                            binding.btnLogin.isVisible = true
                            binding.progressBar.isVisible = false

                            AlertDialog.Builder(this@LoginActivity).setMessage(it.errorMessage)
                                .create()
                                .show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.reactionState.collect {
                    binding.ivReaction.setImageResource(it.reactionResourceId)
                    binding.root.setBackgroundColor(applicationContext.getColor(it.backgroundColor))
                }
            }
        }
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                UserLogin(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            )
        }
    }
}