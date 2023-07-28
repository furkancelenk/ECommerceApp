package com.furkancelenk.ecommerce.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.furkancelenk.ecommerce.R
import com.furkancelenk.ecommerce.databinding.FragmentPaymentBinding
import com.google.android.material.snackbar.Snackbar

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

    private val monthList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private val yearList = listOf(2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032)

    private var monthValue = 0
    private var yearValue = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val monthAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, monthList)
        val yearAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, yearList)

        with(binding) {

            actExpireOnMonth.setAdapter(monthAdapter)
            actExpireOnYear.setAdapter(yearAdapter)

            actExpireOnMonth.setOnItemClickListener { _, _, position, _ ->
                monthValue = monthList[position]
            }

            actExpireOnYear.setOnItemClickListener { _, _, position, _ ->
                yearValue = yearList[position]
            }

            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnPayNow.setOnClickListener {

                val cardHolderName = etCardholderName.text.toString()
                val creditCardNumber = etCreditCardNumber.text.toString()
                val cvcCode = etCvcCode.text.toString()
                val address = etAddress.text.toString()

                if (
                    cardHolderName.isNotEmpty() &&
                    creditCardNumber.isNotEmpty() &&
                    cvcCode.isNotEmpty() &&
                    address.isNotEmpty() &&
                    monthValue != 0 &&
                    yearValue != 0
                ) {
                    findNavController().navigate(R.id.paymentToPaymentSuccess)
                } else {
                    Snackbar.make(requireView(),
                        "Satın alma işlemini tamamlamak için lütfen boş alanları doldurunuz!",
                        2000).show()
                }
            }
        }
    }
}