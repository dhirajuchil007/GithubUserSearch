package com.example.githubusersearch.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.githubusersearch.databinding.FragmentFollowersFragmentsBinding
import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.ui.UserDetailsViewModel
import com.example.githubusersearch.ui.adapters.UserListAdapter
import com.example.githubusersearch.ui.screens.UserDetailsActivity
import com.example.githubusersearch.ui.states.UserDetailsState
import com.example.githubusersearch.ui.states.UserFollowState
import kotlinx.coroutines.launch


class FollowersFragments : Fragment() {
    companion object {
        private const val TAG = "FollowersFragments"
    }

    lateinit var viewModel: UserDetailsViewModel
    lateinit var binding: FragmentFollowersFragmentsBinding
    lateinit var adapter: UserListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersFragmentsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[UserDetailsViewModel::class.java]
        setUpAdapter()
        getUserData()
        setUpObserver()
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

    private fun setUpObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.followersState.collect {
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

    private fun getUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is UserDetailsState.ShowUser -> {
                            showFollowers(it.user)
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun showFollowers(user: UserDomainModel?) {
        viewModel.getUserFollowers(user?.username ?: "")

    }
}