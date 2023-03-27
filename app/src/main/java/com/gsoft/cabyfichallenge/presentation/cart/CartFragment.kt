package com.gsoft.cabyfichallenge.presentation.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gsoft.cabyfichallenge.databinding.FragmentCartBinding
import com.gsoft.cabyfichallenge.presentation.cart.adapter.CartAdapter
import com.gsoft.cabyfichallenge.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CartFragment : Fragment(com.gsoft.cabyfichallenge.R.layout.fragment_cart) {

    @Inject
    lateinit var cartAdapter: CartAdapter

    private val viewModel: CartViewModel by viewModels()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)
        observe()
        setView()
        fetchProducts()
    }

    private fun setView() {
        binding.bBuy.setOnClickListener {
            viewModel.simulatePayment()
        }
        cartAdapter.setOnItemClickListener {
            viewModel.deleteFromCart(it)
        }
        binding.rvCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireActivity())

        }
    }

    private fun observe() {
        observeProducts()
        observeState()
        observePrice()
        observePayment()
    }

    private fun observeState() {
        viewModel.isLoading.observe(viewLifecycleOwner) { value ->
            binding.cartLoading.isVisible = value == true
        }
        viewModel.isError.observe(viewLifecycleOwner) { it ->
            if (it) {
                view?.let {
                    viewModel.errorMessage.value?.let { it1 ->
                        Snackbar.make(
                            requireView(),
                            it1, Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun observePrice() {
        viewModel.totalPrice.observe(viewLifecycleOwner) { value ->
            val totalAmount = getString(com.gsoft.cabyfichallenge.R.string.total_amount, value)
            binding.tTotal.text = totalAmount
        }
    }

    private fun fetchProducts() {
        viewModel.fetchProducts()
    }


    private fun observeProducts() {
        viewModel.cart.observe(viewLifecycleOwner) { cart ->
            if (!cart.isNullOrEmpty()) {
                cartAdapter.submitList(cart)
            } else {
                cartAdapter.submitList(emptyList())
            }
        }
    }

    private fun observePayment() {
        viewModel.payment.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    handleLoading(true)
                }
                is Resource.Success -> {
                    handleLoading(false)
                    viewModel.wipeCart()
                    view?.let {
                        Snackbar.make(
                            it,
                            com.gsoft.cabyfichallenge.R.string.payment_success,
                            Snackbar.LENGTH_SHORT
                        )
                    }?.show()
                }
                is Resource.Failure -> {
                    handleLoading(false)
                    val exception = resource.exception
                    view?.let {
                        Snackbar.make(
                            it,
                            exception.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                    }?.show()

                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.cartLoading.isVisible = isLoading
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}