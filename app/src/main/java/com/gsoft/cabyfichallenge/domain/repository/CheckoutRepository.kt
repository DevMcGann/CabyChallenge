package com.gsoft.cabyfichallenge.domain.repository

import com.gsoft.cabyfichallenge.data.model.PaymentResponse
import com.gsoft.cabyfichallenge.util.Resource

interface CheckoutRepository {
    suspend fun getPaymentMockedResponse(): Resource<PaymentResponse>
}