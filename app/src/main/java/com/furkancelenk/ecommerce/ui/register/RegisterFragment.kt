package com.furkancelenk.ecommerce.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.furkancelenk.ecommerce.R
import com.furkancelenk.ecommerce.data.model.User
import com.furkancelenk.ecommerce.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(binding) {

            btnRegister.setOnClickListener {

                clearErrors()

                val email = etEmail.text.toString()
                val nickname = etNickname.text.toString()
                val phoneNumber = etPhoneNumber.text.toString()
                val pass = etPassword.text.toString()
                val verifyPass = etVerifyPassword.text.toString()

                when {
                    email.isEmpty() -> tilEmail.error = "E-Mail cannot be empty!"
                    nickname.isEmpty() -> tilNickname.error = "Nickname cannot be empty!"
                    phoneNumber.isEmpty() -> tilPhoneNumber.error = "Phone Number cannot be empty!"
                    pass.isEmpty() -> tilPassword.error = "Password cannot be empty!"
                    pass != verifyPass -> {
                        tilPassword.error = "Password is not matching"
                        tilVerifyPassword.error = "Password is not matching"
                    }
                    else -> viewModel.register(User(email, nickname, phoneNumber), pass)
                }
            }
        }
    }

    private fun clearErrors() = with(binding) {
        tilEmail.isErrorEnabled = false
        tilNickname.isErrorEnabled = false
        tilPhoneNumber.isErrorEnabled = false
        tilPassword.isErrorEnabled = false
        tilVerifyPassword.isErrorEnabled = false
    }

    private fun initObservers() {
        viewModel.isRegister.observe(viewLifecycleOwner) {
            if (it.first) {
                Toast.makeText(requireContext(), it.second, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.register_to_login)
            } else {
                Toast.makeText(requireContext(), it.second, Toast.LENGTH_SHORT).show()
            }
        }
    }
}