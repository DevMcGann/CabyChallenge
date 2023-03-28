package com.gsoft.cabyfichallenge.presentation.cart

import androidx.lifecycle.*
import com.gsoft.cabyfichallenge.data.datasource.local.CalculatePrice
import com.gsoft.cabyfichallenge.data.model.PaymentResponse
import com.gsoft.cabyfichallenge.domain.entity.CartItemCount
import com.gsoft.cabyfichallenge.domain.usecase.*
import com.gsoft.cabyfichallenge.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsCountUseCase: GetCartItemsCountUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val wipeCartItemUseCase: WipeCartItemUseCase,
    private val checkoutUseCase: CheckoutUseCase
) : ViewModel() {


    val cart: MutableLiveData<List<CartItemCount>> = MutableLiveData()

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    private val _payment = MutableLiveData<Resource<PaymentResponse>>()
    val payment: LiveData<Resource<PaymentResponse>> = _payment


    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData("")

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            isLoading.value = true
            isError.value = false
            try {
                getCartItemsCountUseCase.invoke().collect {
                    var list: List<CartItemCount> = it
                    var total = 0.0
                    if (it.isNotEmpty()) {
                        val updatedPrices = CalculatePrice(it)
                        list = updatedPrices
                        for (item in list) {
                            total += item.price
                        }
                    }
                    _totalPrice.value = total
                    cart.postValue(list)
                    isLoading.value = false
                    isError.value = false
                }
            } catch (e: Exception) {
                isLoading.value = false
                isError.value = true
                errorMessage.value = e.message
            }
        }
    }

    fun deleteFromCart(code: String) {
        isError.value = false
        isLoading.value = true
        viewModelScope.launch {
            try {
                isLoading.value = false
                isError.value = false
                deleteCartItemUseCase.invoke(code)
                fetchProducts()
            } catch (e: Exception) {
                isLoading.value = false
                isError.value = true
                errorMessage.value = e.message
            }
        }
    }

    fun simulatePayment(){
        viewModelScope.launch {
            _payment.value = Resource.Loading
            try {
                _payment.value = checkoutUseCase.invoke()
            }catch (e:Exception){
                _payment.value = Resource.Failure(e)
            }

        }
    }


    fun wipeCart(){
        viewModelScope.launch {
            //yeah... pretty awful, but it is just to simulate a long call to an API
            runBlocking { delay(5000) }
            wipeCartItemUseCase.invoke()
        }
    }


}
