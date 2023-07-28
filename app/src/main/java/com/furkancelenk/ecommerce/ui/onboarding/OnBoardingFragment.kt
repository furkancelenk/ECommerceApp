package com.furkancelenk.ecommerce.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.furkancelenk.ecommerce.R
import com.furkancelenk.ecommerce.databinding.FragmentOnboardingBinding

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        val adapter = ViewPagerAdapter(requireActivity())

        with(binding) {
            vpOnBoarding.adapter = adapter
            wormDotsIndicator.attachTo(vpOnBoarding)
            vpOnBoarding.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> {
                            tvSkip.setOnClickListener {
                                findNavController().navigate(R.id.on_boarding_to_login)
                            }
                            tvPrevious.visibility = View.GONE
                            tvNext.setOnClickListener {
                                vpOnBoarding.currentItem = + 1
                            }
                        }
                        1 -> {
                            tvSkip.visibility = View.VISIBLE
                            tvSkip.setOnClickListener {
                                findNavController().navigate(R.id.on_boarding_to_login)
                            }
                            tvPrevious.visibility = View.VISIBLE
                            tvNext.text = "Next"
                            tvPrevious.setOnClickListener {
                                vpOnBoarding.currentItem = - 1
                            }
                            tvNext.setOnClickListener {
                                vpOnBoarding.currentItem =
                                    vpOnBoarding.currentItem + 1
                            }
                        }
                        else -> {
                            tvSkip.visibility = View.INVISIBLE
                            tvNext.text = "Finish"
                            tvNext.setOnClickListener {
                                vpOnBoarding.currentItem =
                                    vpOnBoarding.currentItem + 1
                                findNavController().navigate(R.id.on_boarding_to_login)
                            }
                            tvPrevious.setOnClickListener {
                                vpOnBoarding.currentItem =
                                    vpOnBoarding.currentItem - 1
                            }
                        }
                    }
                }
            })
        }

    }
}