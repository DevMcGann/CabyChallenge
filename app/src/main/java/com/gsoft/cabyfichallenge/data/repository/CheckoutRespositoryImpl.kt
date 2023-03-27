package com.gsoft.cabyfichallenge.data.repository

import com.gsoft.cabyfichallenge.data.datasource.remote.ProductApi
import com.gsoft.cabyfichallenge.data.model.PaymentResponse
import com.gsoft.cabyfichallenge.domain.repository.CheckoutRepository
import com.gsoft.cabyfichallenge.util.Resource
import javax.inject.Inject

class CheckoutRespositoryImpl @Inject constructor(
    private val apiService: ProductApi,
) : CheckoutRepository {
    override suspend fun getPaymentMockedResponse(): Resource<PaymentResponse> {
        return try {
            val response = apiService.simulateCheckout()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}