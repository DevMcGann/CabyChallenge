package com.gsoft.cabyfichallenge.data.datasource.remote

import com.gsoft.cabyfichallenge.data.model.PaymentResponse
import com.gsoft.cabyfichallenge.data.model.ProductsResponse
import retrofit2.http.GET

interface ProductApi {
    @GET("Products.json")
     suspend fun getProducts() : ProductsResponse

    @GET("Products.json")
     suspend fun simulateCheckout(): PaymentResponse


}