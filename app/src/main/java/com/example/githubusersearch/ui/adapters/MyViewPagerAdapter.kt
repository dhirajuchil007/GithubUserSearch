package com.example.githubusersearch.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubusersearch.ui.fragments.DescriptionFragment
import com.example.githubusersearch.ui.fragments.FollowersFragments
import com.example.githubusersearch.ui.fragments.FollowingFragment

class MyViewPagerAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DescriptionFragment()
            }

            1 -> {
                FollowersFragments()
            }

            2 -> {
                FollowingFragment()
            }

            else -> {
                DescriptionFragment()
            }
        }
    }
}