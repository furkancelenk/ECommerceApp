package com.furkancelenk.ecommerce.ui.search

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkancelenk.ecommerce.common.ProductDiffUtilCallback
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.databinding.ItemProductBinding

class SearchAdapter(
    private val onProductClick: (Product) -> Unit,
) : ListAdapter<Product, SearchAdapter.ProductViewHolder>(ProductDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(currentList[position])

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {

            with(binding) {

                tvTitle.text = product.title
                val formattedPrice = product.salePrice.formatPrice()

                if (product.saleState == 1) {
                    tvPrice.visibility = View.VISIBLE
                    tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvPrice.text = "${product.price.formatPrice()}₺"

                    if (formattedPrice[0].toString() == "0") {
                        tvPriceSale.text = "${formattedPrice.substring(2, formattedPrice.length)}₺"
                    } else {
                        tvPriceSale.text = "${product.salePrice.formatPrice()}₺"
                    }
                } else {
                    tvPrice.visibility = View.GONE
                    tvPriceSale.text = "${product.price}₺"
                }

                Glide.with(binding.imgProduct).load(product.image).into(binding.imgProduct)

                root.setOnClickListener { onProductClick(product) }
            }
        }
    }

    private fun Double.formatPrice(): String = String.format("%.3f", this)
}