package com.furkancelenk.ecommerce.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.furkancelenk.ecommerce.R
import androidx.navigation.fragment.findNavController
import com.furkancelenk.ecommerce.MainActivity
import com.furkancelenk.ecommerce.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel by lazy { ProfileViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        binding.btnSignOut.setOnClickListener {
            profileViewModel.signOut()
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun initObservers() = with(binding) {
        profileViewModel.user.observe(viewLifecycleOwner) {
            tvEmail.text = it.email
            tvNickname.text = it.nickname
            tvPhoneNumber.text = it.phoneNumber
        }
    }
}