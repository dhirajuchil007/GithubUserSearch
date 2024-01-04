package com.example.githubusersearch.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.githubusersearch.databinding.FragmentFollowingBinding
import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.ui.UserDetailsViewModel
import com.example.githubusersearch.ui.adapters.UserListAdapter
import com.example.githubusersearch.ui.screens.UserDetailsActivity
import com.example.githubusersearch.ui.states.UserDetailsState
import com.example.githubusersearch.ui.states.UserFollowState
import kotlinx.coroutines.launch


class FollowingFragment : Fragment() {
    lateinit var binding: FragmentFollowingBinding
    lateinit var viewModel: UserDetailsViewModel
    lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[UserDetailsViewModel::class.java]
        setUpAdapter()
        setUpObserver()
        getUserData()
    }

    private fun setUpAdapter() {
        binding.apply {
            adapter = UserListAdapter {
                UserDetailsActivity.start(requireContext(), it)
            }
            rvFollowers.adapter = adapter
            rvFollowers.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                requireContext(),
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun getUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect {
                    when (it) {
                        is UserDetailsState.ShowUser -> {
                            showFollowing(it.user)
                        }

                        else -> {
                            //no-op
                        }
                    }
                }
            }
        }
    }

    private fun setUpObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.followingState.collect {
                    when (it) {
                        is UserFollowState.ShowFollow -> {
                            it.followers?.let { it1 -> adapter.submitData(it1) }
                        }

                        is UserFollowState.Error -> {}
                        UserFollowState.Loading -> {}
                    }
                }
            }
        }
    }

    private fun showFollowing(user: UserDomainModel?) {
        viewModel.getUserFollowing(user?.username ?: "")

    }
}