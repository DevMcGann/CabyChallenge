package com.gsoft.cabyfichallenge.domain.usecase


import com.gsoft.cabyfichallenge.domain.repository.ProductRepository
import javax.inject.Inject

class WipeCartItemUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun invoke()  {
        return  productRepository.clearCart()
    }
}