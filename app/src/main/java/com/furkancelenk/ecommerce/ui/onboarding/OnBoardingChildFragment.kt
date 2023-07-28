package com.furkancelenk.ecommerce.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.furkancelenk.ecommerce.R
import com.furkancelenk.ecommerce.databinding.FragmentOnboardingChildBinding

class OnBoardingChildFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingChildBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOnboardingChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            binding.animationView.setAnimation(it.getInt(ANIM_PATH, R.raw.orangeonlineshooping))
        }
    }

    companion object {

        private const val ANIM_PATH = "ANIM_PATH"

        @JvmStatic
        fun newInstance(animPath: Int) =
            OnBoardingChildFragment().apply {
                arguments = Bundle().apply {
                    putInt(ANIM_PATH, animPath)
                }
            }
    }
}