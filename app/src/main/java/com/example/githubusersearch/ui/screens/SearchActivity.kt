package com.example.githubusersearch.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubusersearch.R
import com.example.githubusersearch.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSearch.setOnClickListener {
            if (binding.edtUsername.text.toString().isNotEmpty()) {
                UserDetailsActivity.start(this, binding.edtUsername.text.toString())
            } else {
                binding.edtUsername.error = getString(R.string.please_enter_username)
            }
        }


    }
}