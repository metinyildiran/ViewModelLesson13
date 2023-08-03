package com.example.viewmodellesson13.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson13.UserAdapter
import com.example.viewmodellesson13.databinding.ActivityUsersBinding
import com.example.viewmodellesson13.viewmodel.UsersViewModel
import kotlinx.coroutines.flow.collect
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
    }

    private fun observeAdapterState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.adapter.collect {
                    when (it) {
                        is UsersViewModel.Adapter.Idle -> {}
                        is UsersViewModel.Adapter.Remove -> {
                            adapter.notifyItemRangeRemoved(0, it.position)
                        }

                        is UsersViewModel.Adapter.Add -> {}
                    }
                }
            }
        }
    }

    private fun observeUserList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userList.collect {
                    adapter = UserAdapter(this@UsersActivity, it) { position ->
                        viewModel.removeItem(position)
                    }
                    binding.rvUsers.adapter = adapter
                }
            }
        }
    }
}