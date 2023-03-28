package com.gsoft.cabyfichallenge.domain.usecase

import com.gsoft.cabyfichallenge.domain.entity.CartItemDB
import com.gsoft.cabyfichallenge.domain.repository.ProductRepository
import javax.inject.Inject

class SaveProductsAsCartItemUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun invoke(cartItemDB: CartItemDB)  {
        return  productRepository.addProductToCart(cartItemDB)
    }
}