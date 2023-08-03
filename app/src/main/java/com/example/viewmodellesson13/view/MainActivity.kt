package com.example.viewmodellesson13.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.viewmodellesson13.databinding.ActivityMainBinding
import com.example.viewmodellesson13.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.count.observe(this) {
            binding.tvCounter.text = it.toString()
        }

        binding.btnCounter.setOnClickListener {
            viewModel.counterButtonClicked()
        }

        viewModel.fruits.observe(this) {
            binding.spItems.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
        }

        binding.btnAddFruit.setOnClickListener {
            viewModel.addFruit(binding.etFruit.text.toString())
        }

        binding.spItems.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.fruit.observe(this@MainActivity) {
                    binding.tvFruit.text = viewModel.setFruit(viewModel.fruits.value!![position])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun goToCounterPage(view: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }

    fun goToUsersActivity(view: View) {
        startActivity(Intent(this, UsersActivity::class.java))
    }
}