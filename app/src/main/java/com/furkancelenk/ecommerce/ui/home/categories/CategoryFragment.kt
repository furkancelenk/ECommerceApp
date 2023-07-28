package com.furkancelenk.ecommerce.ui.home.categories

import android.os.Bundle
import android.provider.MediaStore.Video.VideoColumns.CATEGORY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.furkancelenk.ecommerce.R
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.databinding.FragmentCategoryBinding
import com.furkancelenk.ecommerce.ui.home.HomeFragmentDirections

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private lateinit var binding: FragmentCategoryBinding

    private val categoryProductsViewModel by lazy { CategoryProductsViewModel(requireContext()) }

    private val categoryProductsAdapter by lazy { CategoryProductsAdapter(onProductClick = ::onProductClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(CATEGORY)?.let {
            if (it == "All") categoryProductsViewModel.getProductsByCategory()
            else categoryProductsViewModel.getProductsByCategory(it)
        }

        initObservers()
    }

    private fun initObservers() {
        categoryProductsViewModel.productList.observe(viewLifecycleOwner) {
            categoryProductsAdapter.submitList(it)
            binding.rvCategory.adapter = categoryProductsAdapter
        }
    }

    private fun onProductClick(product: Product) {
        val action = HomeFragmentDirections.homeToDetail(product)
        findNavController().navigate(action)
    }
}