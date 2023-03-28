package com.gsoft.cabyfichallenge.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsoft.cabyfichallenge.data.model.Product
import com.gsoft.cabyfichallenge.data.model.ProductsResponse
import com.gsoft.cabyfichallenge.domain.ProductMapper
import com.gsoft.cabyfichallenge.domain.usecase.GetProductsUseCase
import com.gsoft.cabyfichallenge.domain.usecase.SaveProductsAsCartItemUseCase
import com.gsoft.cabyfichallenge.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val saveProductsAsCartItemUseCase: SaveProductsAsCartItemUseCase
): ViewModel() {


    private val _products = MutableLiveData<Resource<ProductsResponse>>()
    val products: LiveData<Resource<ProductsResponse>> = _products

    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()


    init {
        fetchProducts()
    }
    
    fun fetchProducts(){
        viewModelScope.launch {
            _products.value = Resource.Loading
            _products.value = getProductsUseCase.invoke()
        }
    }

    //when user click on an item´s Cart item
    fun saveToCart(product: Product){
        val item = ProductMapper.oneToDb(product)
        isError.value = false
        isLoading.value = true
        viewModelScope.launch {
            try {
                isLoading.value = false
                isError.value = false
                saveProductsAsCartItemUseCase.invoke(item)
            }catch (e:Exception){
                isLoading.value = false
                isError.value = true
            }
        }
    }


}
