package com.gsoft.cabyfichallenge.domain.useCase

import com.gsoft.cabyfichallenge.data.model.PaymentResponse
import com.gsoft.cabyfichallenge.domain.repository.CheckoutRepository
import com.gsoft.cabyfichallenge.util.Resource
import javax.inject.Inject

class CheckoutUseCase@Inject constructor(
    private val checkoutRepository: CheckoutRepository
) {
    suspend fun invoke() : Resource<PaymentResponse> {
        return  checkoutRepository.getPaymentMockedResponse()
    }
}