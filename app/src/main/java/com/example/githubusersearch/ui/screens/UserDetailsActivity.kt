package com.example.githubusersearch.ui.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.githubusersearch.R
import com.example.githubusersearch.databinding.ActivityUserDetailsBinding
import com.example.githubusersearch.ui.UserDetailsViewModel
import com.example.githubusersearch.ui.adapters.MyViewPagerAdapter
import com.example.githubusersearch.ui.states.UserDetailsState
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserDetailsBinding
    lateinit var viewModel: UserDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserDetailsViewModel::class.java]

        val userName = intent.getStringExtra(USERNAME)

        observeState()

        setClickListeners()

        viewModel.getUser(userName)
    }

    private fun setClickListeners() {
        binding.btnRetry.setOnClickListener {
            finish()
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    when (state) {
                        UserDetailsState.Loading -> {
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                successGroup.visibility = View.GONE
                                errorGroup.visibility = View.GONE
                            }
                        }

                        is UserDetailsState.Error -> {
                            binding.apply {
                                progressBar.visibility = View.GONE
                                successGroup.visibility = View.GONE
                                errorGroup.visibility = View.VISIBLE
                                (state.message ?: getString(R.string.unknown_error)).also {
                                    txtError.text = it
                                }
                            }
                        }

                        is UserDetailsState.ShowUser -> {
                            binding.apply {
                                progressBar.visibility = View.GONE
                                successGroup.visibility = View.VISIBLE
                                errorGroup.visibility = View.GONE
                                state.user?.let {
                                    Glide.with(this@UserDetailsActivity)
                                        .load(it.avatarUrl).circleCrop()
                                        .into(imgAvatar)
                                    txtName.text = it.name
                                    txtUserName.text = it.username
                                    setUpViewPager(it.followers, it.following)
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun setUpViewPager(followers: Int, following: Int) {
        binding.apply {
            viewPager.adapter = MyViewPagerAdapter(this@UserDetailsActivity)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Description"
                    1 -> tab.text = "$followers Followers"
                    2 -> tab.text = "$following Following"
                }
            }.attach()
        }
    }

    companion object {
        private const val TAG = "UserDetailsActivity"

        const val USERNAME = "user"
        fun start(context: Context, userName: String) {
            val intent = Intent(context, UserDetailsActivity::class.java)
            intent.putExtra(USERNAME, userName)
            context.startActivity(intent)
        }
    }


}