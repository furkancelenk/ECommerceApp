package com.furkancelenk.ecommerce.ui.home.categories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.data.repository.ProductRepository
import kotlinx.coroutines.launch

class CategoryProductsViewModel(context: Context) : ViewModel() {

    private val productRepository = ProductRepository(context)

    private var _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    fun getProductsByCategory(category: String? = null) {
        viewModelScope.launch {
            productRepository.getProductsByCategory(category)
            _productList = productRepository.productList
        }
    }
}