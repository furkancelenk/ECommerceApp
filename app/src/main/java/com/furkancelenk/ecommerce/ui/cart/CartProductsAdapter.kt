package com.furkancelenk.ecommerce.ui.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkancelenk.ecommerce.common.ProductDiffUtilCallback
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.databinding.ItemCartBinding

class CartProductsAdapter(
    private val onProductClick: (Product) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
) : ListAdapter<Product, CartProductsAdapter.CartViewHolder>(ProductDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    inner class CartViewHolder(private var binding: ItemCartBinding) :
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

                ivDelete.setOnClickListener { onDeleteClick(product.id ?: 1) }
            }
        }
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(currentList[position])

    private fun Double.formatPrice(): String = String.format("%.3f", this)
}