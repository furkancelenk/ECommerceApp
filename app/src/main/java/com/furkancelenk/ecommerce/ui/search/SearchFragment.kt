package com.furkancelenk.ecommerce.ui.search

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.furkancelenk.ecommerce.data.model.Product
import com.furkancelenk.ecommerce.databinding.FragmentSearchBinding
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val searchProductAdapter by lazy { SearchAdapter(onProductClick = ::onProductClick) }

    private val searchViewModel by lazy { SearchViewModel(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) searchViewModel.products()
                else {
                    binding.micsOn.visibility = View.GONE
                    binding.micsOff.visibility = View.VISIBLE
                    searchViewModel.searchProducts(newText)
                }
                return false
            }
        })

        binding.micsOn.visibility = View.GONE

        binding.micsOff.setOnClickListener {
            binding.micsOn.visibility = View.VISIBLE
            startSpeechToText()
        }
    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_TO_TEXT)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_TO_TEXT && resultCode == AppCompatActivity.RESULT_OK) {
            val speechResults = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = speechResults?.get(0) ?: ""
            binding.searchView.setQuery(spokenText, false)
        }
    }

    companion object {
        private const val REQUEST_CODE_SPEECH_TO_TEXT = 100
    }

    private fun initObservers() {
        searchViewModel.productList.observe(viewLifecycleOwner) {
            if (it != null) {
                searchProductAdapter.submitList(it)
                binding.recyclerViewProductsSearch.adapter = searchProductAdapter
            } else {
                Toast.makeText(requireContext(), "Empty List", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onProductClick(product: Product) {
        val action = SearchFragmentDirections.searchToDetail(product)
        findNavController().navigate(action)
    }
}
