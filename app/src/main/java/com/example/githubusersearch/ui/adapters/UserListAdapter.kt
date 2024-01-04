package com.example.githubusersearch.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersearch.databinding.LayoutUserListItemBinding
import com.example.githubusersearch.domain.model.UserDomainModel

class UserListAdapter(private val callback: (userName: String) -> Unit) :
    PagingDataAdapter<UserDomainModel, UserListAdapter.UserViewHolder>(UserListCallbackDiff()) {
    inner class UserViewHolder(private val binding: LayoutUserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserDomainModel) {

            binding.apply {
                txtUserName.text = user.username
                Glide.with(binding.root)
                    .load(user.avatarUrl).circleCrop()
                    .into(imageAvatar)

                root.setOnClickListener {
                    callback(user.username)
                }
            }


        }
    }

    class UserListCallbackDiff : DiffUtil.ItemCallback<UserDomainModel>() {
        override fun areItemsTheSame(oldItem: UserDomainModel, newItem: UserDomainModel): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(
            oldItem: UserDomainModel,
            newItem: UserDomainModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutUserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}