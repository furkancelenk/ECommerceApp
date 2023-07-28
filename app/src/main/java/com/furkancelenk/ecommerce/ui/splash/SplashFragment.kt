package com.furkancelenk.ecommerce.ui.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.furkancelenk.ecommerce.R
import com.furkancelenk.ecommerce.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences

    private lateinit var binding: FragmentSplashBinding

    private val viewModel: SplashViewModel by viewModels()

    private var isFirstLogin = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("com.furkancelenk.ecommerce",
            Context.MODE_PRIVATE)

        isFirstLogin = sharedPref.getBoolean("isFirstLogin", false)

        initObservers()

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.checkCurrentUser()
        }, 3000)
    }

    private fun initObservers() {
        viewModel.isCurrentUserExist.observe(viewLifecycleOwner) {
            when {
                !isFirstLogin -> navigate(R.id.splash_to_on_boarding)
                it -> navigate(R.id.splash_to_home)
                !it -> navigate(R.id.splash_to_login)
            }
        }
    }

    private fun navigate(root: Int) {
        findNavController().navigate(root)
        sharedPref.edit().putBoolean("isFirstLogin", true).apply()
    }
}


