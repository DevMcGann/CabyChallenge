package com.gsoft.cabyfichallenge.domain.usecase

import com.gsoft.cabyfichallenge.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun invoke( code:String)  {
        val item = productRepository.getRandomItemByCode(code)
        if(item != null) productRepository.deleteProductFromCart(item)
    }
}