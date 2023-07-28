package com.furkancelenk.ecommerce.ui.paymentsuccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.furkancelenk.ecommerce.R
import com.furkancelenk.ecommerce.databinding.FragmentPaymentSuccessBinding

class PaymentSuccessFragment : Fragment() {

    private lateinit var binding: FragmentPaymentSuccessBinding

    private val paymentSuccessViewModel by lazy { PaymentSuccessViewModel(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPaymentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnContinueShopping.setOnClickListener {
                paymentSuccessViewModel.clearBag()
                findNavController().navigate(R.id.paymentSuccessToHome)
            }
        }
    }
}