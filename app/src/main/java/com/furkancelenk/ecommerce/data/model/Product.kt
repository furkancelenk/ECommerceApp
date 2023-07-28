package com.furkancelenk.ecommerce.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cart")
@Parcelize
data class Product(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "count")
    val count: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "salePrice")
    val salePrice: Double,

    @ColumnInfo(name = "rate")
    val rate: Double,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "user")
    val user: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "image_three")
    @SerializedName("image_three")
    val imageThree: String,

    @ColumnInfo(name = "image_two")
    @SerializedName("image_two")
    val imageTwo: String,

    @ColumnInfo(name = "sale_state")
    @SerializedName("sale_state")
    val saleState: Int,
) : Parcelable