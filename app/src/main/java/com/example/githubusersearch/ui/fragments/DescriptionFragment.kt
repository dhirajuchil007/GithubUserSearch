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
import com.example.githubusersearch.R
import com.example.githubusersearch.databinding.FragmentDescriptionBinding
import com.example.githubusersearch.ui.UserDetailsViewModel
import com.example.githubusersearch.ui.states.UserDetailsState
import kotlinx.coroutines.launch


class DescriptionFragment : Fragment() {
    lateinit var binding: FragmentDescriptionBinding
    lateinit var viewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescriptionBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[UserDetailsViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is UserDetailsState.ShowUser -> {
                            binding.apply {
                                txtDescription.text = it.user?.description
                            }
                        }

                        else -> {
                            binding.apply {
                                txtDescription.text = getString(R.string.no_description)
                            }
                        }
                    }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}