package com.furkancelenk.ecommerce.ui.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.data.repository.ProductRepository

class DetailViewModel(context: Context) : ViewModel() {

    private val productRepository = ProductRepository(context)

    private var _isProductAddedCart = MutableLiveData<Boolean>()
    val isProductAddedCart: LiveData<Boolean>
        get() = _isProductAddedCart

    init {
        _isProductAddedCart = productRepository.isProductAddedCart
    }

    fun addProductToCart(product: Product) = productRepository.addProductToCart(product)
}

