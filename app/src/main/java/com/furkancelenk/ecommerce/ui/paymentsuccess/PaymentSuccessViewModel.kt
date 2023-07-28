package com.furkancelenk.ecommerce.ui.paymentsuccess

import android.content.Context
import androidx.lifecycle.ViewModel
import com.furkancelenk.ecommerce.data.repository.ProductRepository

class PaymentSuccessViewModel(context: Context) : ViewModel() {

    private val productRepository = ProductRepository(context)

    fun clearBag() {
        productRepository.clearCart()
    }
}