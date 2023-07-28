package com.furkancelenk.ecommerce.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkancelenk.ecommerce.common.ProductDiffUtilCallback
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.databinding.ItemProductSaleBinding

class SaleProductsAdapter(private val onProductClick: (Product) -> Unit) :
    ListAdapter<Product, SaleProductsAdapter.ProductViewHolder>(ProductDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(currentList[position])

    inner class ProductViewHolder(
        private val binding: ItemProductSaleBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {

            with(binding) {

                tvTitle.text = product.title
                val string = String.format("%.3f", product.salePrice)

                tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvPrice.text = String.format("%.3f₺", product.price)

                if (string[0].toString() == "0") {
                    tvPriceSale.text = "${string.substring(2, string.length)}₺"
                } else {
                    tvPriceSale.text = String.format("%.3f₺", product.salePrice)
                }

                Glide.with(imgProduct).load(product.image).into(imgProduct)
                root.setOnClickListener { onProductClick(product) }
            }
        }
    }
}