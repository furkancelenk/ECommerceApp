package com.furkancelenk.ecommerce.common

import androidx.recyclerview.widget.DiffUtil
import com.furkancelenk.ecommerce.data.model.Product

object ProductDiffUtilCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}