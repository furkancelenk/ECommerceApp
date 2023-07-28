package com.furkancelenk.ecommerce.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.furkancelenk.ecommerce.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val args: DetailFragmentArgs by navArgs()

    private val detailViewModel by lazy { DetailViewModel(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(binding) {
            args.product.let { product ->
                tvTitle.text = product.title
                btnAddToCart.text = if (product.saleState == 1) {
                    String.format("%.3f₺", product.salePrice)
                } else {
                    "${product.price}₺"
                }
                tvDescription.text = product.description
                Glide.with(imgProduct).load(product.image).into(imgProduct)

                btnAddToCart.setOnClickListener {
                    detailViewModel.addProductToCart(product)
                }
            }

            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun initObservers() {
        detailViewModel.isProductAddedCart.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigateUp()
                Snackbar.make(requireView(), "The product is added to Cart!", 1000).show()
            }
            else Snackbar.make(requireView(), "The product is already in Cart!", 1000).show()
        }
    }
}