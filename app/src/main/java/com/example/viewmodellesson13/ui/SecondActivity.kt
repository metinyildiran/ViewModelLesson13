package com.example.viewmodellesson13.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson13.databinding.ActivitySecondBinding
import com.example.viewmodellesson13.viewmodel.SecondViewModel
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val viewModel: SecondViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()

        observers()

        observeTimer()

        observeState()

        observeMessage()
    }

    private fun observeMessage() {
        lifecycleScope.launch {
            viewModel.message.collect {
                AlertDialog.Builder(this@SecondActivity).setMessage(it).create().show()
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                Toast.makeText(this@SecondActivity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeTimer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.timer.collect {
                    binding.tvTimer.text = it.toString()
                }
            }
        }
    }

    private fun observers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.counter.collect {
                    binding.tvCounter.text = it.toString()
                }
            }
        }
    }

    private fun listeners() {
        binding.btnShowMessage.setOnClickListener {
            viewModel.showMessage()
        }

        binding.btnGetData.setOnClickListener {
            viewModel.setState()
        }

        binding.btnCount.setOnClickListener {
            viewModel.increase()
        }
    }
}