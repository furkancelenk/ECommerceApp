package com.furkancelenk.ecommerce.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.data.repository.ProductRepository

class HomeViewModel(context: Context) : ViewModel() {

    private val productRepository = ProductRepository(context)

    private var _saleProductList = MutableLiveData<List<Product>>()
    val saleProductList: LiveData<List<Product>>
        get() = _saleProductList

    private var _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>>
        get() = _categoryList

    init {
        saleProducts()
        categories()
    }

    private fun saleProducts() {
        productRepository.getSaleProducts()
        _saleProductList = productRepository.saleProductList
    }

    private fun categories() {
        productRepository.getCategories()
        _categoryList = productRepository.categoryList
    }
}