package com.furkancelenk.ecommerce.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.furkancelenk.ecommerce.R
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val cartProductsAdapter by lazy { CartProductsAdapter(
        onProductClick = ::onProductClick,
        onDeleteClick = ::onDeleteClick
    ) }

    private val cartViewModel by lazy { CartViewModel(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        binding.btnBuyNow.setOnClickListener { findNavController().navigate(R.id.cartToPayment) }
    }

    private fun initObservers() {
        cartViewModel.cartProductList.observe(viewLifecycleOwner) {
            cartProductsAdapter.submitList(it)
            binding.rvCartProducts.adapter = cartProductsAdapter
        }
    }

    private fun onProductClick(product: Product) {
        val action = CartFragmentDirections.cartToDetail(product)
        findNavController().navigate(action)
    }

    private fun onDeleteClick(id: Int) {
        cartViewModel.deleteFromCart(id)
    }
}