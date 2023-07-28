package com.furkancelenk.ecommerce.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.data.source.local.CartDAO
import com.furkancelenk.ecommerce.data.source.local.CartRoomDatabase
import com.furkancelenk.ecommerce.data.source.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(context: Context) {

    val productList = MutableLiveData<List<Product>>()

    val saleProductList = MutableLiveData<List<Product>>()

    val categoryList = MutableLiveData<List<String>>()

    val cartProductList = MutableLiveData<List<Product>>()

    var isProductAddedCart = MutableLiveData<Boolean>()

    private val apiService = RetrofitClient.apiService

    private val cartDAO: CartDAO? =
        CartRoomDatabase.productsCartRoomDatabase(context)?.productsCartDAOInterface()

    fun getProducts() {
        apiService.getProducts().enqueue(object : Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.body().isNullOrEmpty()) productList.value = emptyList()
                else productList.value = response.body()?.mapList()
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.d("Failure", t.message.orEmpty())
            }
        })
    }

    fun getSaleProducts() {
        apiService.getProducts().enqueue(object : Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.body().isNullOrEmpty()) saleProductList.value = emptyList()
                else saleProductList.value =
                    response.body()?.filter { it.saleState == 1 }?.mapList()
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.d("Failure", t.message.orEmpty())
            }
        })
    }

    fun getProductsByCategory(category: String?) {
        category?.let { category ->
            apiService.getProductsByCategory(category = category)
                .enqueue(object : Callback<List<Product>> {

                    override fun onResponse(
                        call: Call<List<Product>>,
                        response: Response<List<Product>>,
                    ) {
                        if (response.body().isNullOrEmpty()) productList.value = emptyList()
                        else productList.value = response.body()?.mapList()
                    }

                    override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                        Log.d("Failure", t.message.orEmpty())
                    }
                })
        } ?: kotlin.run {
            apiService.getProducts().enqueue(object : Callback<List<Product>> {

                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>,
                ) {
                    if (response.body().isNullOrEmpty()) productList.value = emptyList()
                    else productList.value = response.body()?.mapList()
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Log.d("Failure", t.message.orEmpty())
                }
            })
        }
    }

    fun searchProducts(query: String) {
        apiService.searchProducts(query).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.body().isNullOrEmpty()) productList.value = emptyList()
                else productList.value = response.body()?.mapList()
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.d("Failure", t.message.orEmpty())
            }
        })
    }

    fun getCategories() {
        apiService.getCategories().enqueue(object : Callback<List<String>> {
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>,
            ) {
                if (response.body().isNullOrEmpty()) categoryList.value = emptyList()
                else categoryList.value = response.body()
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("Failure", t.message.orEmpty())
            }
        })
    }

    fun addProductToCart(product: Product) {
        cartDAO?.getProductsTitlesCart()?.let {
            isProductAddedCart.value = if (it.contains(product.title).not()) {
                cartDAO.addProductCart(product)
                true
            } else false
        }
    }

    fun deleteFromCart(id: Int) = cartDAO?.deleteFromCart(id)

    fun getCartProducts() {
        cartDAO?.getCartProducts()?.let {
            cartProductList.value = it
        }
    }

    fun clearCart() = cartDAO?.clearCart()

    private fun List<Product>.mapList(): List<Product> =
        this.map {
            Product(
                it.id,
                it.category,
                it.count,
                it.description,
                it.price,
                if (it.saleState == 1) (it.price * 80) / 100 else 0.0,
                it.rate,
                it.title,
                it.user,
                it.image,
                it.imageThree,
                it.imageTwo,
                it.saleState
            )
        }
}