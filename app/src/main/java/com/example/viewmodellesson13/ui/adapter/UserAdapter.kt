package com.example.viewmodellesson13.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.viewmodellesson13.databinding.UserListItemBinding
import com.example.viewmodellesson13.data.model.User

class UserAdapter(
    private val context: Context,
    private val users: List<User>,
    private val onRemove: (user: User) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    class MyViewHolder(binding: UserListItemBinding) : ViewHolder(binding.root) {
        val ivUser = binding.ivUser
        val tvUserFullName = binding.tvUserFullName
        val ivRemove = binding.ivRemove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = users[position]

        holder.ivUser.load(user.profileImageUrl)
        holder.tvUserFullName.text = "${user.name} ${user.surname}"
        holder.ivRemove.setOnClickListener {
            onRemove.invoke(user)
        }
    }
}