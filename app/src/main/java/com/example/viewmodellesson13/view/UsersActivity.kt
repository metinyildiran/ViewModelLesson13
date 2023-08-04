package com.example.viewmodellesson13.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson13.adapter.UserAdapter
import com.example.viewmodellesson13.databinding.ActivityUsersBinding
import com.example.viewmodellesson13.model.User
import com.example.viewmodellesson13.viewmodel.UsersViewModel
import kotlinx.coroutines.launch

class UsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersBinding
    private val viewModel: UsersViewModel by viewModels()

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeUserList()

        observeAdapterState()

        listeners()
    }

    private fun listeners() {
        binding.fab.setOnClickListener {
            val user = User(
                "Metin",
                "Yıldıran",
                "https://images.unsplash.com/photo-1603415526960-f7e0328c63b1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80"
            )

            viewModel.addItem(user)
        }
    }

    private fun observeAdapterState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.adapter.collect {
                    when (it) {
                        is UsersViewModel.Adapter.Idle -> {}
                        is UsersViewModel.Adapter.Remove -> {
                            adapter.notifyItemRemoved(it.position)
                        }

                        is UsersViewModel.Adapter.Add -> {
                            adapter.notifyItemInserted(viewModel.userList.value.lastIndex)
                        }
                    }
                }
            }
        }
    }

    private fun observeUserList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userList.collect {
                    adapter = UserAdapter(this@UsersActivity, it) { user ->
                        viewModel.removeItem(user)
                    }
                    binding.rvUsers.adapter = adapter
                }
            }
        }
    }
}