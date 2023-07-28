package com.furkancelenk.ecommerce.ui.cart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.data.repository.ProductRepository

class CartViewModel(context: Context) : ViewModel() {

    private val productRepository = ProductRepository(context)

    private var _cartProductList = MutableLiveData<List<Product>>()
    val cartProductList: LiveData<List<Product>>
        get() = _cartProductList

    init {
        cartProducts()
    }

    private fun cartProducts() {
        productRepository.getCartProducts()
        _cartProductList = productRepository.cartProductList
    }

    fun deleteFromCart(id: Int) {
        productRepository.deleteFromCart(id)
        cartProducts()
    }
}

