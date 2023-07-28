package com.furkancelenk.ecommerce.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furkancelenk.ecommerce.data.model.Product

@Dao
interface CartDAO {

    @Insert
    fun addProductCart(productCart: Product)

    @Query("SELECT * FROM cart")
    fun getCartProducts(): List<Product>?

    @Query("SELECT title FROM cart")
    fun getProductsTitlesCart(): List<String>?

    @Query("DELETE FROM cart WHERE id = :idInput")
    fun deleteFromCart(idInput: Int)

    @Query("DELETE FROM cart")
    fun clearCart()
}