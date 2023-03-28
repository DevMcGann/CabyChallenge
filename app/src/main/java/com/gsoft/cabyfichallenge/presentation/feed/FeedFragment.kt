package com.gsoft.cabyfichallenge.presentation.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gsoft.cabyfichallenge.R
import com.gsoft.cabyfichallenge.databinding.FragmentFeedBinding
import com.gsoft.cabyfichallenge.presentation.feed.adapter.ProductAdapter
import com.gsoft.cabyfichallenge.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : Fragment(R.layout.fragment_feed) {

    @Inject
    lateinit var productAdapter:  ProductAdapter

    private val viewModel: FeedViewModel by viewModels()

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFeedBinding.bind(view)
        observe()

        setView()

        fetchProducts()
    }

    private fun setView() {

        binding.bCart.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_cartFragment)
        }

        productAdapter.setOnItemClickListener {
            viewModel.saveToCart(it)
        }
        
        binding.rvFeed.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireActivity())

        }

    }

    private fun observe(){
        observeProducts()
        observeState()
    }

    private fun observeState() {
        viewModel.isLoading.observe(viewLifecycleOwner){value ->
            binding.progressBar.isVisible = value == true
        }
        viewModel.isError.observe(viewLifecycleOwner){ it ->
            if(it){
                view?.let { Snackbar.make(requireView(), R.string.error_adding_to_cart, Snackbar.LENGTH_LONG).show() }
            }
        }
    }

    private fun fetchProducts(){
        viewModel.fetchProducts()
    }


    private fun observeProducts(){
        viewModel.products.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    handleLoading(true)
                }
                is Resource.Success -> {
                    handleLoading(false)
                    productAdapter.submitList(resource.data.products)
                }
                is Resource.Failure -> {
                    handleLoading(false)
                    val exception = resource.exception
                    view?.let { Snackbar.make(it, exception.message.toString(), Snackbar.LENGTH_LONG) }?.show()

                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean){
        if(isLoading){
            binding.progressBar.isVisible = true
            binding.progressBar.isGone = false
        }else{
            binding.progressBar.isGone = true
            binding.progressBar.isVisible = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}