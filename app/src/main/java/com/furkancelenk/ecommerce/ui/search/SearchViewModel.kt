package com.furkancelenk.ecommerce.ui.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.data.repository.ProductRepository

class SearchViewModel(context: Context) : ViewModel() {

    private val productRepository = ProductRepository(context)

    private var _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>?>
        get() = _productList

    init {
        products()
    }

    fun products() {
        productRepository.getProducts()
        _productList = productRepository.productList
    }

    fun searchProducts(query: String) {
        productRepository.searchProducts(query)
        _productList = productRepository.productList
    }
}