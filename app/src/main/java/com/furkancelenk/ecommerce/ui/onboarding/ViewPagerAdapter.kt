package com.furkancelenk.ecommerce.ui.onboarding

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.furkancelenk.ecommerce.R

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    private val animList = listOf(R.raw.orangeonlineshooping, R.raw.orangecheckbox, R.raw.orangedoorbox)

    override fun getItemCount() = 3

    override fun createFragment(position: Int) =
        OnBoardingChildFragment.newInstance(animList[position])
}