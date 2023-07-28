package com.furkancelenk.ecommerce.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.furkancelenk.ecommerce.R
import com.furkancelenk.ecommerce.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(binding) {

            btnLogin.setOnClickListener {

                clearErrors()

                val email = etEmail.text.toString()
                val pass = etPassword.text.toString()

                when {
                    email.isEmpty() -> tilEmail.error = "E-Mail cannot be empty!"
                    email.isNotEmpty() && pass.isEmpty() -> tilPassword.error =
                        "Password cannot be empty!"
                    else -> viewModel.login(email, pass)
                }
            }

            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.login_to_register)
            }
        }
    }

    private fun initObservers() {
        viewModel.isLogin.observe(viewLifecycleOwner) {
            if (it.first) {
                Toast.makeText(requireContext(), it.second, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.login_to_home)
            } else {
                Toast.makeText(requireContext(), it.second, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearErrors() = with(binding) {
        tilEmail.isErrorEnabled = false
        tilPassword.isErrorEnabled = false
    }
}