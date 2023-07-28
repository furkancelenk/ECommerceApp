package com.furkancelenk.ecommerce.data.source.remote

import com.furkancelenk.ecommerce.data.model.Product
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    @POST("get_products_by_user.php")
    @FormUrlEncoded
    fun getProducts(
        @Field("user") user: String = "furkancelenk",
    ): Call<List<Product>>

    @POST("get_products_by_user_and_category.php")
    @FormUrlEncoded
    fun getProductsByCategory(
        @Field("user") user: String = "furkancelenk",
        @Field("category") category: String,
    ): Call<List<Product>>

    @POST("search_product_by_user.php")
    @FormUrlEncoded
    fun searchProducts(
        @Field("query") query: String,
        @Field("user") user: String = "furkancelenk",
    ): Call<List<Product>>

    @POST("get_categories_by_user.php")
    @FormUrlEncoded
    fun getCategories(
        @Field("user") user: String = "furkancelenk",
    ): Call<List<String>>
}