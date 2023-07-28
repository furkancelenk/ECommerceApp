package com.furkancelenk.ecommerce.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.databinding.FragmentHomeBinding
import com.furkancelenk.ecommerce.ui.home.categories.CategoryPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val saleProductsAdapter by lazy { SaleProductsAdapter(onProductClick = ::onProductClick) }

    private val homeViewModel by lazy { HomeViewModel(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()

        initObservers()
    }

    private fun initObservers() = with(binding) {

        homeViewModel.saleProductList.observe(viewLifecycleOwner) {
            saleProductsAdapter.submitList(it)
        }

        homeViewModel.categoryList.observe(viewLifecycleOwner) {
            val tempCategories = it.toMutableList()
            tempCategories.add(0, "All")
            vpCategory.adapter =
                CategoryPagerAdapter(this@HomeFragment, tempCategories)
            TabLayoutMediator(tabLayout, vpCategory) { tab, position ->
                tab.text = tempCategories[position]
            }.attach()
        }
    }

    private fun onProductClick(product: Product) {
        val action = HomeFragmentDirections.homeToDetail(product)
        findNavController().navigate(action)
    }

    private fun initViewPager() {
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.85f + r * 0.15f)
        }

        binding.vpSaleProducts.apply {
            adapter = saleProductsAdapter
            currentItem = 2
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(compositePageTransformer)
        }
    }
}
